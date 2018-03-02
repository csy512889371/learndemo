package redis.core.impl;

import com.ctoedu.redis.core.AbstractRedis;
import com.ctoedu.redis.core.RedisSetClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Date:2016年11月4日 下午1:51:58
 * Version:1.0
 */
@Component
public class RedisSetClientImpl extends AbstractRedis implements RedisSetClient {

	@SuppressWarnings("unchecked")
	public <T> long sadd(final String key,final int seconds, final T... values){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notEmpty(values,"values is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				byte[] byteKey = getBytesKey(key);
				long returnValue = 0;
				for (T v :values) {
					list.add(toBytes(v));
				}
				returnValue = connection.sAdd(byteKey, (byte[][])list.toArray());
				if (seconds != 0 ) {
					connection.expire(byteKey, seconds);
				}
				logger.debug("lpush key {} = {} ",key,values);
				return returnValue;
			}
			
		});
	}

	@SuppressWarnings("unchecked")
	public <T> long sadd(final String key, final T... values){
		return sadd(key, 0, values);
	}

	@SuppressWarnings("unchecked")
	public <T> Set<T> smembers(final String key){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Set<T>>() {
			public Set<T> doInRedis(
					RedisConnection connection) throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Set<byte[]> set = connection.sMembers(byteKey);
				Set<T> returnValue = new HashSet<T>();
				for (byte[] bytes :set) {
					returnValue.add((T)toObject(bytes));
				}
				return returnValue;
			}
		});
	}
}
