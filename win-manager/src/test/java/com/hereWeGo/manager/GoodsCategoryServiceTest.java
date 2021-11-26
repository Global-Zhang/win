package com.hereWeGo.manager;

import com.alibaba.druid.util.Utils;
import com.hereWeGo.common.result.FileResult;
import com.hereWeGo.manager.pojo.GoodsCategory;
import com.hereWeGo.manager.service.BrandService;
import com.hereWeGo.manager.service.GoodsCategoryService;
import com.hereWeGo.manager.service.GoodsService;
import com.hereWeGo.manager.service.UploadService;
import com.hereWeGo.manager.vo.GoodsCategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class GoodsCategoryServiceTest {

    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private GoodsService goodsService;

    @Test
    public void testSelectGoodsCategoryListForView(){
        List<GoodsCategoryVo> gcvList = goodsCategoryService.selectCategoryListForView();
    }

    @Test
    public void upload() throws IOException {
        File file = new File("C:\\Users\\zhangheng\\Desktop\\unnamed.jpg");

        String filename = "unamed.jpg";
        String date = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(LocalDateTime.now());
        filename = date + System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
        uploadService.upload(new FileInputStream(file),filename);
    }

    @Test
    public void testGoodsList(){
        System.out.println(brandService.selectBrandList());

    }
}
