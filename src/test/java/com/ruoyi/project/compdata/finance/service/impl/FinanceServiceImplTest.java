package com.ruoyi.project.compdata.finance.service.impl;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.project.compdata.finance.mapper.FinanceMapper;
import com.ruoyi.project.compdata.finance.service.IFinanceService;
import com.ruoyi.project.compdata.finance.vo.TypeProfitAnalyVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = RuoYiApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class FinanceServiceImplTest {

    @Autowired
    IFinanceService financeService;

    @Autowired
    FinanceMapper financeMapper;

    @Test
    void selectTypeProfitAnalyVoList(){
    }

    @Test
    void bigDecimalComp() {
        BigDecimal b1 = new BigDecimal(0.15);
        BigDecimal b2 = new BigDecimal(0.45);
        Float rate = b1.divide(b2,2).floatValue();
        System.out.println("result:"+rate);
    }

    @Test
    void getPreMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date m = c.getTime();
        String curMonthStr = format.format(m);
        c.add(Calendar.MONTH, -1);
        m = c.getTime();
        String preMonthStr = format.format(m);
        System.out.println("本月为："+curMonthStr+",上月为："+preMonthStr);
    }

    @Test
    void getPreMonthStr(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            // 注意格式需要与上面一致，不然会出现异常
            date = format.parse("2021年1月");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String preMonthStr = format.format(m);
        System.out.println("上月月份为："+preMonthStr);
    }
}