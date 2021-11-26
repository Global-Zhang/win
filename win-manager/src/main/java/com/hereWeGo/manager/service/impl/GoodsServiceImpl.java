package com.hereWeGo.manager.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.manager.mapper.GoodsMapper;
import com.hereWeGo.manager.pojo.Goods;
import com.hereWeGo.manager.pojo.GoodsExample;
import com.hereWeGo.manager.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Goods> selectGoodsList(){
        return goodsMapper.selectByExample(new GoodsExample());
    }

    //商品新增保存
    @Override
    public BaseResult saveGoods(Goods goods) {
        //通过前端传来id判断是否重复提交
        if (null != goods.getGoodsId()) return BaseResult.error();
        //保存前清空缓存
        redisTemplate.delete(redisTemplate.keys("goods*"));
        //UEidtor文本转意
        if(!StringUtils.isEmpty(goods.getGoodsContent())){
            goods.setGoodsContent(HtmlUtils.htmlEscape(goods.getGoodsContent(),"UTF-8"));
        }
        //保存
        int result = goodsMapper.insertSelective(goods);
        BaseResult baseResult = new BaseResult();
        if (result > 0){
            baseResult = BaseResult.success();
            baseResult.setMessage(String.valueOf(goods.getGoodsId()));
        }else{
            baseResult = BaseResult.error();
        }
        return baseResult;
    }
    //商品列表——分页查询
    @Override
    public BaseResult selectGoodsListByPage(Goods goods, Integer pageNum, Integer pageSize) {
        /**
         * 商品列表RedisKey
         * 1.无条件查询
         *      goods：pageNum_:pageSize_:catId_:brandId_:goodsName_:
         * 2.条件查询
         *      goods：pageNum_:pageSize_:catId_123:brandId_:goodsName_:
         *      goods：pageNum_:pageSize_:catId_:brandId_123:goodsName_:
         *      goods：pageNum_:pageSize_:catId_:brandId_:goodsName_123:
         *      goods：pageNum_:pageSize_:catId_123:brandId_123:goodsName_:
         *      goods：pageNum_:pageSize_:catId_123:brandId_:goodsName_123:
         *      goods：pageNum_:pageSize_:catId_:brandId_123:goodsName_123:
         *      goods：pageNum_:pageSize_:catId_123:brandId_123:goodsName_123:
         */

        //查询条件数组，改变catId_:    brandId_:    goodsName_:
        String[] goodsKeyArr = new String[]{
                "goods：pageNum_"+pageNum+":pageSize_"+pageSize+":",
                "catId_:",
                "brandId_:",
                "goodsName_:"
        };
        //构建分页对象
        PageHelper.startPage(pageNum,pageSize);
        //创建查询对象
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        //分类参数
        if (null != goods.getCatId() && 0 != goods.getCatId()){
            criteria.andCatIdEqualTo(goods.getCatId());
            //修改查询条件
            goodsKeyArr[1] = "catId_" + goods.getCatId() + ":";
        }
        //品牌参数
        if (null != goods.getBrandId() && 0 != goods.getBrandId()){
            criteria.andBrandIdEqualTo(goods.getBrandId());
            //修改查询条件
            goodsKeyArr[2] = "brandId_" + goods.getBrandId() + ":";
        }
        //关键词
        if (!StringUtils.isEmpty(goods.getGoodsName())){
            criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
            //修改查询条件
            goodsKeyArr[3] = "goodsName_" + goods.getGoodsName() + ":";
        }
        //重新组合-redisKey
        String goodsListKey = Arrays.stream(goodsKeyArr).collect(Collectors.joining());
        //获取redis模板，操作value
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        //查询缓存，如果缓存中存在数据，直接返回
        String pageInfoGoodsJson = valueOperations.get(goodsListKey);
        //如果缓存存在，从redis中提取
        if (!StringUtils.isEmpty(pageInfoGoodsJson)){
            /**
             * ※ ※ ※PageInfo来源于PageHelper插件，和Mysql交互,不能和redis数据交互
             * ※ ※ ※所以BaseResult.success存入object对象而不是list
             */
            return BaseResult.success(JsonUtil.jsonStr2Object(pageInfoGoodsJson,PageInfo.class));
        }
        //判断查下结果是否为空,不为空放入分页对象
        List<Goods> list = goodsMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)){
            /**
             *  ※ ※ ※ PageInfo来源于PageHelper插件，和Mysql交互,不能和redis数据交互
             */
            PageInfo<Goods> pageInfo = new PageInfo<>(list);
            //将数据存入redis
            valueOperations.set(goodsListKey,JsonUtil.object2JsonStr(pageInfo));
            return BaseResult.success(pageInfo);
        }else{
            //如果没有数据，将空数据放入缓存，设置失效时间60s
            valueOperations.set(
                                goodsListKey,
                                JsonUtil.object2JsonStr(new PageInfo<>(new ArrayList<Goods>())),
                        60,
                                TimeUnit.SECONDS);
        }
        return BaseResult.error();
    }


}
