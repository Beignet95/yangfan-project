package com.ruoyi.project.compdata.finance.service.impl;

import com.ruoyi.common.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class MathTest {
    public static void main(String[] args) {
        if((Double) 0.9 % 1 > 0){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    void test1(){
        String timeStr = "2021-2";
        Date time = DateUtils.dateTime("yyyy-MM",timeStr);
        System.out.println(time);
    }
}
