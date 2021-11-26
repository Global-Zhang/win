package com.hereWeGo.manager.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hereWeGo.common.result.FileResult;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.manager.mapper.GoodsCategoryMapper;
import com.hereWeGo.manager.pojo.GoodsCategory;
import com.hereWeGo.manager.pojo.GoodsCategoryExample;
import com.hereWeGo.manager.service.GoodsCategoryService;
import com.hereWeGo.manager.vo.GoodsCategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${goods.category.list.key}")
    private String goodsCategoryListKey;

    @Override
    public List<GoodsCategoryVo> selectCategoryListForView() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        //查询redis缓存是否有数据，有则直接返回，无则从数据库提取
        String gcvListJson = valueOperations.get(goodsCategoryListKey);
        if (!StringUtils.isEmpty(gcvListJson)){
            return JsonUtil.jsonToList(gcvListJson,GoodsCategoryVo.class);
        }
        //创建查询对象
        GoodsCategoryExample example = new GoodsCategoryExample();
        /*****************************传统for循环***************************/
        //设置查询条件
        example.createCriteria().andParentIdEqualTo((short)0).andLevelEqualTo((byte)1);
        //查询顶级分类
        List<GoodsCategory> gcList01 = goodsCategoryMapper.selectByExample(example);
        List<GoodsCategoryVo> gcvList01 = new ArrayList<>();
        for (GoodsCategory gc01: gcList01) {
            GoodsCategoryVo gcv01 = new GoodsCategoryVo();
            //把GoodCategory对象转换成GoodsCategoryVo对象
            BeanUtils.copyProperties(gc01,gcv01);
            //清空查询条件
            example.clear();
            //设置查询条件
            example.createCriteria().andParentIdEqualTo(gc01.getId()).andLevelEqualTo((byte)2);
            //查询二级分类
            List<GoodsCategory> gcList02 = goodsCategoryMapper.selectByExample(example);
            List<GoodsCategoryVo> gcvList02 = new ArrayList<>();
            for (GoodsCategory gc02 : gcList02) {
                GoodsCategoryVo gcv02 = new GoodsCategoryVo();
                BeanUtils.copyProperties(gc02,gcv02);
                //清空查询条件
                example.clear();
                //设置查询条件
                example.createCriteria().andParentIdEqualTo(gc02.getId()).andLevelEqualTo((byte)3);
                //查询二级分类
                List<GoodsCategory> gcList03 = goodsCategoryMapper.selectByExample(example);
                List<GoodsCategoryVo> gcvList03 = new ArrayList<>();
                for (GoodsCategory gc03 : gcList03) {
                    GoodsCategoryVo gcv03 = new GoodsCategoryVo();
                    BeanUtils.copyProperties(gc03,gcv03);
                    gcvList03.add(gcv03);
                }
                //把三级分类放入二级分类中
                gcv02.setChildren(gcvList03);
                gcvList02.add(gcv02);
            }
            //把二级分类放入一级分类中
            gcv01.setChildren(gcvList02);
            gcvList01.add(gcv01);
        }

        /****************************传统for循环****************************/


        /***************************java8 新特性**************************
        //查询所有商品分类
        List<GoodsCategory> list = goodsCategoryMapper.selectByExample(example);
        //将GoodsCategory对象转成GoodsCategoryVo对象
        List<GoodsCategoryVo> gcvList = list.stream().map(e -> {
            GoodsCategoryVo gcv = new GoodsCategoryVo();
            BeanUtils.copyProperties(e, gcv);
            return gcv;
        }).collect(Collectors.toList());
        //将List<GoodsCategoryVo>转成Map<parentId,List<GoodsCategoryVo>>
        //按parentId分组，key就是parentId,值就是parentId对应的List<GoodsCategoryVo>
        Map<Short, List<GoodsCategoryVo>> map =
                gcvList.stream().collect(Collectors.groupingBy(GoodsCategoryVo::getParentId));
        //循环，给children赋值
        gcvList.forEach(e->e.setChildren(map.get(e.getId())));
        //拦截器，返回level为1的list，也就是顶级分类
        List<GoodsCategoryVo> gcvList01 =
                gcvList.stream().filter(e -> 1 == e.getLevel()).collect(Collectors.toList());
        return gcvList01;
        **************************java8 新特性*************************/

        //放入redis缓存
        valueOperations.set(goodsCategoryListKey, JsonUtil.object2JsonStr(gcvList01));
        return gcvList01;
    }

    @Override
    public GoodsCategory selectCategoryById( Short id) {
        return goodsCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(GoodsCategory record) {
        return goodsCategoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Short id) {
        return goodsCategoryMapper.deleteByPrimaryKey(id);
    }
    //商品分类-查询所有分类
    @Override
    public List<GoodsCategory> selectCategoryList() {
        return goodsCategoryMapper.selectByExample(new GoodsCategoryExample());
    }

    //商品分类，新增分类，保存分类
    @Override
    public int categorySave(GoodsCategory goodsCategory) {
        //删除redis缓存数据
        redisTemplate.delete(redisTemplate.keys("goods*"));
        return goodsCategoryMapper.insertSelective(goodsCategory);
    }

    @Override
    public List<GoodsCategory> selectCategoryTopList() {
        //创建查询对象
        GoodsCategoryExample example = new GoodsCategoryExample();
        //设置查询条件
        example.createCriteria().andParentIdEqualTo((short)0);
        return goodsCategoryMapper.selectByExample(example);
    }

    @Override
    public List<GoodsCategory> selectCategoryByParentId(Short parentId) {
        //创建查询对象
        GoodsCategoryExample example = new GoodsCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        return goodsCategoryMapper.selectByExample(example);
    }



}
