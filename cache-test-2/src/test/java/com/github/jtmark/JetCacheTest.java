/**
 * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;

/**
 * <p>
 * Description: 
 * </p>
 * @author majintao
 * @version 1.0
 * @Date 2018年6月30日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class JetCacheTest {
    
    private static final Logger logger = LoggerFactory.getLogger(JetCacheTest.class);

    @CreateCache(name="test", expire = 100,cacheType = CacheType.REMOTE)
    private Cache<Long, String> stringCache;
    
    @Test
    public void test1() {
        stringCache.put(101L, "abc");
        logger.info("===> string cache:{}", stringCache.get(101L));
    }
}
