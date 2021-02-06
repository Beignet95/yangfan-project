package com.ruoyi.project.system.net.zwIpFiter.config;

import com.ruoyi.common.xss.IPInterceptor;
import com.ruoyi.framework.web.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by Beignet on 2017/9/8.
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    //将自定义的拦截器定义为一个bean
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new IPInterceptor();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        // 多个拦截器组成一个拦截器链
//        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /** 后面的全部链接
//        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }

}