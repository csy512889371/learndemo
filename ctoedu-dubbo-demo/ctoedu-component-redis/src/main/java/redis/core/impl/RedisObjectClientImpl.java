package redis.core.impl;

import com.ctoedu.redis.core.AbstractRedis;
import com.ctoedu.redis.core.RedisObjectClient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Component;

/**
 *
 * Date:2016年11月4日 下午1:51:22
 * Version:1.0
 */
@Component
public class RedisObjectClientImpl extends AbstractRedis implements RedisObjectClient {

	/**
	* 设置一个对象 可以是string 
	* @param key
	* @param obj
	* @param clazz 
	* @param seconds 超时时间 0 代表永不过期
	*/
	public <T> void set(final byte[] key,final T obj,final long seconds){
		redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(key, toBytes(obj));
				if (seconds > 0 ) {
					connection.expire(key, seconds);
				}
				logger.debug("set object key {} = {} ", key,obj);
				return null;
			}
		});
	}
	/**
	* 设置一个对象 可以是string 
	* @param key
	* @param obj
	* @param clazz 
	*/
	public <T> void set(final byte[] key,final T obj){
		this.set(key, obj, 0);
	}
	
	/**
	* @param key
	 */
	public <T> T get(final byte[] key){
		return redisTemplate.execute(new RedisCallback<T>() {
			@SuppressWarnings("unchecked")
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				T returnVaue = null;
				if (connection.exists(key)) {
					returnVaue = (T)toObject(connection.get(key)) ;
					logger.debug("get object key {} = {} ", key,returnVaue);
				}
				return returnVaue;
			}
		});
	} 
	
	/**
	* @param key
	* @param seconds 
	 */
	public <T> T get(final byte[] key,final long seconds){
		return redisTemplate.execute(new RedisCallback<T>() {
			@SuppressWarnings("unchecked")
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				T returnVaue = null;
				if (connection.exists(key)) {
					returnVaue = (T)toObject(connection.get(key)) ;
					//每次获得，重置缓存过期时间
	                if (seconds > 0) {  
	                    connection.expire(key, seconds);  
	                } 
					logger.debug("get object key {} = {} ", key,returnVaue);
				}
				return returnVaue;
			}
		});
	}
	
	/**
	* @param key
	 */
	public <T> T mGet(final byte[] key){
		return redisTemplate.execute(new RedisCallback<T>() {
			@SuppressWarnings("unchecked")
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				T returnVaue = (T)connection.mGet(key) ;
				return returnVaue;
			}
		});
	}
	/**
	* 设置字符串
	* @param key
	* @param value
	* @param seconds 超时时间 0 代表永不过期
	*/
	public void setString(final byte[] key,final String value,final long seconds){
		this.set(key, value, seconds);
	}
	/**
	* 设置字符串
	* @param key
	* @param value
	*/
	public void setString(final byte[] key,final String value ){
		this.set(key, value,  0);
	}
}
