
package com.github.jtmark.config;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.github.jtmark.annotation.CacheDuration;
import com.google.common.collect.Sets;

/**
 * 拓展spring cache注解，支持自定义时间配置
 * <p>
 * Description:
 * </p>
 * 
 * @author majintao
 * @version 1.0
 * @Date 2018年6月7日
 */
@Service("redisCacheManager")
public class SpringRedisCacheManager extends RedisCacheManager implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Autowired
    public SpringRedisCacheManager(@Qualifier(value = "redisTemplate") RedisOperations redisOperations) {
        super(redisOperations);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        parseCacheDuration(applicationContext);
    }

    private void parseCacheDuration(ApplicationContext applicationContext) {
        final Map<String, Long> cacheExpires = new HashMap<String, Long>();
        // 获取spring管理的对象
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            final Class clazz = applicationContext.getType(beanName);
            // 解析缓存注解，添加过期时间
            addCacheExpires(clazz, cacheExpires);
        }

        // 设置有效期
        super.setExpires(cacheExpires);
    }

    private void addCacheExpires(final Class clazz, final Map<String, Long> cacheExpires) {
        packageCacheable(clazz, cacheExpires);
        packageCacheConfig(clazz, cacheExpires);
    }

    /**
     * <p>
     * Description:解析Cacheabble
     * </p>
     * 
     * @param clazz
     * @param cacheExpires
     * @author majintao
     * @date 2018年6月18日
     */
    private void packageCacheable(final Class clazz, final Map<String, Long> cacheExpires) {
        // 查询方法Cacheable上绑定的name
        ReflectionUtils.doWithMethods(clazz, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(method);
                Cacheable cacheable = findAnnotation(method, Cacheable.class);
                CacheDuration cd = findAnnotation(method, CacheDuration.class);
                if (cd == null) {
                    return;
                }
                packageExpireMap(cacheExpires, cacheable.value(), cd);
            }

        }, new ReflectionUtils.MethodFilter() {
            @Override
            public boolean matches(Method method) {
                return null != findAnnotation(method, Cacheable.class);
            }
        });
    }

    /**
     * <p>
     * Description: 解析CacheConfig
     * </p>
     * 
     * @param clazz
     * @param cacheExpires
     * @author majintao
     * @date 2018年6月18日
     */
    private void packageCacheConfig(final Class clazz, final Map<String, Long> cacheExpires) {
        // 查询类级别CacheConfig绑定的name (类级别会覆盖方法级别的声明)
        CacheConfig cc = findAnnotation(clazz, CacheConfig.class);
        if (cc == null) {
            return;
        }

        CacheDuration cd = findAnnotation(clazz, CacheDuration.class);
        if (cd == null) {
            return;
        }

        packageExpireMap(cacheExpires, cc.cacheNames(), cd);
    }

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param cacheExpires
     * @param cacheable
     * @param cd
     * @author majintao
     * @date 2018年6月18日
     */
    private void packageExpireMap(final Map<String, Long> cacheExpires, String[] names, CacheDuration cd) {
        if (names == null || names.length == 0) {
            return;
        }
        Set<String> ccNames = Sets.newHashSet(names);
        for (String name : ccNames) {
            cacheExpires.put(name, cd.duration());
        }
    }

}