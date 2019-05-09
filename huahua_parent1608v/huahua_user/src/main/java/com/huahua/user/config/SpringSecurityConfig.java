package com.huahua.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//WebSecurityConfigurerAdapter 拦截所有请求  按照类中的方法进行处理
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * authorizeRequests 所有 Security全注解配置实现的开始,表示开始说明需要的权限
         * antMatchers拦截路径 permitAll 任何权限都可以访问
         * .and().csrf().disable(); 固定写法 表示csrf 拦截失败
         *csrf  网站攻击
         */
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
