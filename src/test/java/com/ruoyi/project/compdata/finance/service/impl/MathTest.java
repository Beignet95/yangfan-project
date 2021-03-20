package com.ruoyi.project.compdata.finance.service.impl;

import com.ruoyi.common.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class MathTest {
    public static void main(String[] args) {

        BigDecimal totalPrice = new BigDecimal("-155.63");
        BigDecimal salePrice = new BigDecimal("-141.04");
        totalPrice = totalPrice.add(salePrice);
        System.out.println(">>>>>>>>totalPrice:"+totalPrice);


    }

    void test1(){
        String timeStr = "2021-2";
        Date time = DateUtils.dateTime("yyyy-MM",timeStr);
        System.out.println(time);
    }
}
