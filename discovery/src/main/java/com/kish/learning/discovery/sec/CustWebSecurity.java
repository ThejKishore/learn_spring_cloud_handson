package com.kish.learning.discovery.sec;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;


// reference from boot1.5 ... SpringBootWebSecurityConfiguration
//https://www.baeldung.com/spring-boot-security-autoconfiguration
//https://github.com/spring-projects/spring-boot/issues/10306


@Configuration
@Order(2147483642)
public class  CustWebSecurity extends WebSecurityConfigurerAdapter {
    public CustWebSecurity() {
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new RequestMatcher() {
            public boolean matches(HttpServletRequest request) {
                return false;
            }
        });
    }
}