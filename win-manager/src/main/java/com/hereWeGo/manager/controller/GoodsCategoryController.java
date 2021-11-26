package com.hereWeGo.manager.controller;

import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.common.result.FileResult;
import com.hereWeGo.manager.pojo.Brand;
import com.hereWeGo.manager.pojo.Goods;
import com.hereWeGo.manager.pojo.GoodsCategory;
import com.hereWeGo.manager.pojo.GoodsImages;
import com.hereWeGo.manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsImagesService goodsImagesService;
    @Autowired
    private UploadService uploadService;

    //添加商品分类页面的两级货品分类展示
    @RequestMapping("category/add")
    public String categoryAdd(Model model){
        List<GoodsCategory> gcList = goodsCategoryService.selectCategoryTopList();
        model.addAttribute("gcList",gcList);
        return "goods/category/category-add";
    }

    //商品分类，新增分类，级联查询
    @RequestMapping("category/{parentId}")
    @ResponseBody
    public List<GoodsCategory> selectCategoryList(@PathVariable Short parentId){
        return goodsCategoryService.selectCategoryByParentId(parentId);
    }

    //商品分类，新增分类，保存分类
    @RequestMapping("category/save")
    @ResponseBody
    public BaseResult categorySave(GoodsCategory goodsCategory){
        int result = goodsCategoryService.categorySave(goodsCategory);
        return result>0 ? BaseResult.success() : BaseResult.error();
    }

    //商品分类页面三级展示
    @RequestMapping("category/list")
    public String categoryList(Model model){
        model.addAttribute("gcvList",goodsCategoryService.selectCategoryListForView());
        return "goods/category/category-list";
    }
    //商品分类修改-页面跳转-含原信息
    @RequestMapping("category/update/{id}")
    public String categoryUpdate(Model model,@PathVariable Short id){
        model.addAttribute("goodsCategory",goodsCategoryService.selectCategoryById(id));
        List<GoodsCategory> gcList = goodsCategoryService.selectCategoryTopList();
        model.addAttribute("gcList01",gcList);
        return "goods/category/category-update";
    }
    //修改-商品分类
    @RequestMapping("category/update")
    @ResponseBody
    public BaseResult categoryUpdateSubmit(GoodsCategory goodsCategory){
        int result = goodsCategoryService.updateByPrimaryKeySelective(goodsCategory);
        return result>0 ? BaseResult.success() : BaseResult.error();
    }
    //删除-商品分类
    @RequestMapping("category/delete/{id}")
    @ResponseBody
    public BaseResult categoryDelete(@PathVariable Short id){
        int result = goodsCategoryService.deleteByPrimaryKey(id);
        return result>0 ? BaseResult.success() : BaseResult.error();
    }

    //商品-列表-页面跳转
    @RequestMapping("list")
    public String goodsList(Model model){
        //List<GoodsCategory> gcList = goodsCategoryService.selectCategoryList();
        //model.addAttribute("gcList",gcList);

        model.addAttribute("gcList",goodsCategoryService.selectCategoryList());
        model.addAttribute("brandList", brandService.selectBrandList());
        return "goods/goods-list";
    }
    //商品-列表-新增
    @RequestMapping("add")
    public String goodsAdd(Model model){
        List<GoodsCategory> gcList = goodsCategoryService.selectCategoryTopList();
        model.addAttribute("gcList",gcList);
        List<Brand> brandList = brandService.selectBrandList();
        model.addAttribute("brandList",brandList);
        return "goods/goods-add";
    }
    //保存商品
    @RequestMapping("save")
    @ResponseBody
    public BaseResult saveGoods(Goods goods){
        return goodsService.saveGoods(goods);
    }

    //保存商品相册
    @RequestMapping("images/save")
    @ResponseBody
    public BaseResult saveImages(MultipartFile file,Integer goodsId) throws IOException {
        String filename = file.getOriginalFilename();
        String date = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(LocalDateTime.now());
        filename = date + System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
        FileResult result =  uploadService.upload(file.getInputStream(),filename);

        if (!StringUtils.isEmpty(result.getFileUrl())){
            GoodsImages goodsImages = new GoodsImages();
            goodsImages.setImageUrl(result.getFileUrl());
            goodsImages.setGoodsId(goodsId);
            BaseResult baseResult = goodsImagesService.saveGoodsImages(goodsImages);
            return baseResult;
        }else {
            return BaseResult.error();
        }
    }
    //商品列表分页查询
    @RequestMapping("listForPage")
    @ResponseBody
    public BaseResult selectGoodsListByPage(Goods goods,Integer pageNum,Integer pageSize){
        return goodsService.selectGoodsListByPage(goods,pageNum,pageSize);
    }


}
