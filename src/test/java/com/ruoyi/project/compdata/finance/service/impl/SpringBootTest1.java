package com.ruoyi.project.compdata.finance.service.impl;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.system.net.zwIpFiter.domain.SysZwIpFilter;
import com.ruoyi.project.system.net.zwIpFiter.mapper.SysZwIpFilterMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@org.springframework.boot.test.context.SpringBootTest(classes = RuoYiApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootTest1 {
    @Autowired
    SysZwIpFilterMapper sysZwIpFilterMapper;

    @Test
    public void addWihteIp(){
        SysZwIpFilter sysZwIpFilter = new SysZwIpFilter();
        sysZwIpFilter.setMoudle("测试");
        sysZwIpFilter.setMark(1);
        sysZwIpFilter.setIp("0:0:0:0:0:0:0:1");
        sysZwIpFilterMapper.insertSysZwIpFilter(sysZwIpFilter);

    }

}
