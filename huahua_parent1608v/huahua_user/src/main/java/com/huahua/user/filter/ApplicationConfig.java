
package com.huahua.user.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtFilter).addPathPatterns("/**")
            .excludePathPatterns("/**/sendsms/**")
            .excludePathPatterns("/**/register/**")
                .excludePathPatterns("/**/incfollow/**")
                .excludePathPatterns("/**/incfans/**")
            .excludePathPatterns("/**/login");
    }
}


