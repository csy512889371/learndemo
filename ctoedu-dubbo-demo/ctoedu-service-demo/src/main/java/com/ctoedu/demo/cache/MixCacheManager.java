package com.ctoedu.demo.cache;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class MixCacheManager implements CacheManager{
	
	private CacheManager ehCacheManager;
	
	private CacheManager redisCacheManager;
	
	private String redisPrefix = "redis-";
	
	public CacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(CacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

	public CacheManager getRedisCacheManager() {
		return redisCacheManager;
	}

	public void setRedisCacheManager(CacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	public String getRedisPrefix() {
		return redisPrefix;
	}

	public void setRedisPrefix(String redisPrefix) {
		this.redisPrefix = redisPrefix;
	}

	@Override
	public Cache getCache(String name) {
		if(name.startsWith(this.redisPrefix)){
			return redisCacheManager.getCache(name);
		}else{
			return ehCacheManager.getCache(name);
		}
	}

	@Override
	public Collection<String> getCacheNames() {
		Collection<String> cacheNames = new ArrayList<String>();          
        if (redisCacheManager != null) {  
            cacheNames.addAll(redisCacheManager.getCacheNames());  
        }  
        if (ehCacheManager != null) {  
            cacheNames.addAll(ehCacheManager.getCacheNames());  
        }         
        return cacheNames;
	}
	
}
