package com.ruoyi.project.compdata.finance.service.impl;

import java.math.BigDecimal;

public class MathTest {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal("");
        BigDecimal res = a.add(b);
        System.out.println(res);
    }
}
