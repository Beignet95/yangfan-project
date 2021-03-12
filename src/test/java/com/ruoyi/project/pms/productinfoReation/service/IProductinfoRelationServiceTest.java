package com.ruoyi.project.pms.productinfoReation.service;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@SpringBootTest(classes = RuoYiApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class IProductinfoRelationServiceTest {
    @Autowired
    IProductinfoRelationService productinfoRelationService;

    @Test
    public void test1(){
        Map<String, MskuProductinfoRelationVo> map = productinfoRelationService.getSkuMskuMap();
        System.out.println("success!");
    }

}