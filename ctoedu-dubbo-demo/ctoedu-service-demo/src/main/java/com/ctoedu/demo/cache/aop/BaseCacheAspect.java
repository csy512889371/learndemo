package com.ctoedu.demo.cache.aop;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.util.StringUtils;

import com.ctoedu.demo.cache.MixCacheManager;

/**
 * 基础cache切面
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public class BaseCacheAspect{

    protected final Logger log = LoggerFactory.getLogger(getClass());

//    @Autowired
    private MixCacheManager cacheManager;
    private Cache cache;
    protected String cacheName;

    /**
     * 缓存池名称
     *
     * @param cacheName
     */
    public void setCacheName(String cacheName) {

        this.cacheName = cacheName;
    }

    @PostConstruct
    public void init() throws Exception {
        cache = cacheManager.getCache(cacheName);
    }

    public void clear() {
        log.debug("cacheName:{}, cache clear", cacheName);
        this.cache.clear();
    }

    public void evict(String key) {
        log.debug("cacheName:{}, evict key:{}", cacheName, key);
        this.cache.evict(key);
    }

    @SuppressWarnings("unchecked")
	public <T> T get(Object key) {
        log.debug("cacheName:{}, get key:{}", cacheName, key);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Cache.ValueWrapper value = cache.get(key);
        if (value == null) {
            return null;
        }
        return (T) value.get();
    }

    public void put(String key, Object value) {
        log.debug("cacheName:{}, put key:{}", cacheName, key);
        this.cache.put(key, value);
    }

}
