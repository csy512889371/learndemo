package redis.core.impl;

import com.ctoedu.redis.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实际调用redis类
 *
 * Date:2016年11月4日 下午1:52:34
 * Version:1.0
 */
@Component
public class RedisClient extends AbstractRedis{
	@Autowired
    private RedisHashClient redisHashClient;
	@Autowired
    private RedisListClient redisListClient;
	@Autowired
    private RedisObjectClient redisObjectClient;
	@Autowired
    private RedisSetClient redisSetClient;
    
	public RedisHashClient opsForHash(){
		return  redisHashClient;
	}
	public RedisListClient opsForList(){
		return redisListClient;
	}
	public RedisObjectClient opsForObject(){
		return redisObjectClient;
	}
	public RedisSetClient opsForSet(){
		return redisSetClient;
	}
}
