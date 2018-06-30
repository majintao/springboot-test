/*
 * Copyright (c) 2017-2018 Thinkfly
 * FileName: UserMapper.java
 * Author: yaohepeng
 * Date: 18-4-3 上午10:57
 */

package com.github.jtmark.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.github.jtmark.annotation.CacheDuration;
import com.github.jtmark.entity.User;

@Mapper
@CacheConfig(cacheNames = "user")
@CacheDuration(duration = 100)
public interface UserMapper {
    
    @Cacheable(key = "'g_users_'")
    @Select("select * from user")
    List<User> getAllUser();
    
    
    @Cacheable(key = "'g_ak_' + #p0")
    @Select("select * from user where id = #{id}")
    List<User> getUserById(@Param("id")long id);
}
