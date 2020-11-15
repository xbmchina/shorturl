package cn.xbmchina.shorturl.config;

import cn.xbmchina.shorturl.intercept.RequestLimitIntercept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLimitIntercept requestLimitIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLimitIntercept);
    }
}