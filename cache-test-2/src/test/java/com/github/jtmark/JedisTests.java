/**
 * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author majintao
 * @version 1.0
 * @Date 2018年6月12日
 */
public class JedisTests {
    
    private static final Logger logger = LoggerFactory.getLogger(JedisTests.class);

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("Eagle2017");
        jedis.connect();
        Set<String> set = jedis.keys("*");
        for (String s : set) {
            logger.info(s);
            logger.info(jedis.get(s));
            logger.info(jedis.type(s));
            logger.info(jedis.ttl(s) + "");
        }

    }
}
