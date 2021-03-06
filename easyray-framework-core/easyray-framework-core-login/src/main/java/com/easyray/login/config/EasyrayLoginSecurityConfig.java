package com.easyray.login.config;

import com.easyray.auth.annotation.EasyrayNoAuth;
import com.easyray.auth.service.impl.UserDetailServiceImpl;
import com.easyray.login.filter.EasyrayUsernamePasswordAuthenticationFilter;
import com.easyray.login.filter.sms.EasyraySmsAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date: 20-1-30
 * @Author: wyy
 */
@EnableWebSecurity
public class EasyrayLoginSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(EasyrayLoginSecurityConfig.class);

    @Autowired
    private UserDetailServiceImpl userDetailsServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EasyraySmsAuthenticationFilter easyraySmsAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Set<String> allNoAuthMethod = findAllNoAuthMethod();
        logger.debug("these url need no auth : {}", allNoAuthMethod);

        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //swagger
                .antMatchers("/swagger**/**", "/webjars/**", "/v2/**").permitAll()
                //com.easyray.auth.annotation.EasyrayNoAuth注解标注的请求
                .antMatchers(allNoAuthMethod.toArray(new String[0])).permitAll()
                .antMatchers("sys-login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new EasyrayUsernamePasswordAuthenticationFilter(authenticationManagerBean()))
                .addFilter(easyraySmsAuthenticationFilter);
    }

    private Set<String> findAllNoAuthMethod() throws ClassNotFoundException {
        Map<String, Object> beanName_bean_map = applicationContext.getBeansWithAnnotation(Controller.class);
        Set<String> ignoreAuthUriList = new HashSet<>();
        for (Object bean : beanName_bean_map.values()) {
            String className = bean.getClass().getName();

            Class<?> controller = Class.forName(className);
            RequestMapping annotation = controller.getAnnotation(RequestMapping.class);
            String urlPrefix = "";
            if (annotation != null) {
                String[] value = annotation.value();
                urlPrefix = value[0];
            }
            Method[] methods = controller.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(EasyrayNoAuth.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping != null) {
                        String[] value = requestMapping.value();
                        for (String path : value) {
                            path = urlPrefix + (path.startsWith("/") ? path : "/" + path);
                            ignoreAuthUriList.add(path);
                        }
                        continue;
                    }
                    GetMapping getMapping = method.getAnnotation(GetMapping.class);
                    if (getMapping != null) {
                        String[] value = getMapping.value();
                        for (String path : value) {
                            path = urlPrefix + (path.startsWith("/") ? path : "/" + path);
                            if (path.contains("{")) {
                                ignoreAuthUriList.add(path.substring(0, path.indexOf("{")));
                            } else {
                                ignoreAuthUriList.add(path);
                            }
                        }
                        continue;
                    }
                    PostMapping postMapping = method.getAnnotation(PostMapping.class);
                    if (postMapping != null) {
                        String[] value = postMapping.value();
                        for (String path : value) {
                            path = urlPrefix + (path.startsWith("/") ? path : "/" + path);
                            ignoreAuthUriList.add(path);
                        }
                        continue;
                    }
                    PutMapping putMapping = method.getAnnotation(PutMapping.class);
                    if (putMapping != null) {
                        String[] value = putMapping.value();
                        for (String path : value) {
                            path = urlPrefix + (path.startsWith("/") ? path : "/" + path);
                            ignoreAuthUriList.add(path);
                        }
                        continue;
                    }
                    DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
                    if (deleteMapping != null) {
                        String[] value = deleteMapping.value();
                        for (String path : value) {
                            path = urlPrefix + (path.startsWith("/") ? path : "/" + path);
                            if (path.contains("{")) {
                                ignoreAuthUriList.add(path.substring(0, path.indexOf("{")));
                            } else {
                                ignoreAuthUriList.add(path);
                            }
                        }
                    }

                }
            }
        }
        return ignoreAuthUriList;
    }
}
