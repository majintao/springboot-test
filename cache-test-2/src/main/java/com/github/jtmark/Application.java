/**
 * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

/**
 * <p>
 * Description: 
 * </p>
 * @author majintao
 * @version 1.0
 * @Date 2018年6月30日
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "com.github.jtmark")
@EnableCreateCacheAnnotation
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
