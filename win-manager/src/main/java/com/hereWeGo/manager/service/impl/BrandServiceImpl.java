package com.hereWeGo.manager.service.impl;

import com.hereWeGo.manager.mapper.BrandMapper;
import com.hereWeGo.manager.pojo.Brand;
import com.hereWeGo.manager.pojo.BrandExample;
import com.hereWeGo.manager.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> selectBrandList() {
        return brandMapper.selectByExample( new BrandExample());
    }
}
