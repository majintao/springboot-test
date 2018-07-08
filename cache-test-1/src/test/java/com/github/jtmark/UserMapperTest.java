/**
\ * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.github.jtmark.mapper.UserMapper;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author majintao
 * @version 1.0
 * @Date 2018年6月30日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    
    @Test
    public void test1() {
        logger.info(JSON.toJSONString(userMapper.getUserById(1)));
    }

    /**
     * 获取redis存储情况分析
     * <p>
     * Description:
     * </p>
     * @author majintao
     * @date 2018年6月30日
     */
    @Test
    public void test2() {
        Set<String> sets = stringRedisTemplate.keys("*");
        for (String s : sets) {
            logger.info("====");
            logger.info("key:{}", s);
            DataType keyType = stringRedisTemplate.type(s);
            logger.info("type:{}" + keyType);
            if (DataType.STRING == keyType) {  //存储的序列化数据
                logger.info("value:{}", stringRedisTemplate.opsForValue().get(s)); 
            } else if (DataType.ZSET == keyType) {  // spring redis 有个api removeAll，这里应该是存储所有key，ttl为最大的设置的string ttl值
                logger.info("value:{}", stringRedisTemplate.opsForZSet().range(s, 0, -1).toString());
            }
            logger.info("ttl:{}", stringRedisTemplate.getExpire(s));  // 过期时间验证
        }
    }

}
