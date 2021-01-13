package com.rouyi;

import com.ruoyi.project.compdata.domain.Advertising;
import com.ruoyi.project.compdata.service.IAdvertisingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    IAdvertisingService advertisingService;

    @Test
    public void test1(){
        Advertising advertising = new Advertising();
        advertising.setStoreCode("vo.getStoreCode()");
        advertising.setSku("vo.getSku()");
        advertising.setMonth(new Date());
        advertisingService.selectAdvertisingList(advertising);
    }
}
