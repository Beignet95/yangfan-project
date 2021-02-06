package com.ruoyi.common.xss;

import com.ruoyi.common.utils.net.IPUtils;
import com.ruoyi.project.system.net.zwIpFiter.domain.SysZwIpFilter;
import com.ruoyi.project.system.net.zwIpFiter.mapper.SysZwIpFilterMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CREATE BY Beignet ON 2018/5/3
 **/

public class IPInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(IPInterceptor.class.getName());

    @Autowired
    private SysZwIpFilterMapper ipFilterMapper;

    private SysZwIpFilter ipFilter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //过滤ip,若用户在白名单内，则放行
        String ipAddress= IPUtils.getRealIP(request);
        log.info("USER IP ADDRESS IS =>"+ipAddress);
        if(!StringUtils.isNotBlank(ipAddress))
            return false;
        ipFilter=new SysZwIpFilter();
        ipFilter.setMoudle("yangfan-data-analy");//模块
        ipFilter.setIp(ipAddress);//ip地址
        ipFilter.setMark(0);//白名单
        List<SysZwIpFilter> ips=ipFilterMapper.selectSysZwIpFilterList(ipFilter);
        if(ips.isEmpty()){
            response.getWriter().append("<h1 style=\"text-align:center;\">you IP "+ipAddress+" Not allowed!</h1>");
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}