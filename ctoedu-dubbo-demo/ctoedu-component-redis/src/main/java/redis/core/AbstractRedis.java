package redis.core;

import com.ctoedu.redis.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Date:2016年11月4日 上午11:46:15
 * Version:1.0
 */
public abstract class AbstractRedis {
	/**
	 * 日志对象
	 */
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;
    
    /**
	 * 检查给定 key 是否存在。
	 * @param key
	 * @return 若 key 存在，返回 true ，否则返回 false 。
	 */
	public Boolean exists(final byte[] key){
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return	connection.exists(key);
			}
		});
	}
	/**
	 * 删除给定的一个或多个 key 。
	 * 不存在的 key 会被忽略。
	 * @param keys
	 * @return 被删除 key 的数量。
	 */
	public long del(final byte[]... keys){
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				for (byte[] key :keys) {
					list.add(key);
				}
				long returnValue = connection.del((byte[][])list.toArray());
				logger.debug("del key  {} amount {} ", keys,returnValue);
				return returnValue;
			}
		});
	}
	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * @param key
	 * @param seconds 单位秒
	 * @return 设置成功返回true  ，反之false
	 */
	public Boolean expire(final byte[] key,final int seconds){
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.debug("set key {} expire {} ",key,seconds);
				return connection.expire(key, seconds);
			}
		});
	} 
	/**
	 * 移除给定 key 的生存时间
	 * @param key
	 * @return 当生存时间移除成功时true  如果 key 不存在或 key 没有设置生存时间，返回 false
	 */
	public Boolean persist(final byte[] key){
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.debug("persist key {} ",key);
				return connection.persist(key);
			}
		});
	} 
	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。
	 *		        当 key 存在但没有设置剩余生存时间时，返回 -1 。
	 *		      否则，以秒为单位，返回 key 的剩余生存时间。
	 */
	public Long ttl(final byte[] key){
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long returnValue = 0;
				returnValue = connection.ttl(key);
				logger.debug("ttl  key {} = {} ", key,returnValue);
				return returnValue;
			}
		});
	}
	
	/**
	 * 清空所有redis缓存
	 */
	public void flush() {
		redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                connection.flushDb();  
                return "clear done.";  
            }  
        },true);
	}
	
	/**
	 * 根据key的表达式获取所对应的值
	 * @param pattern
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
			public Set<byte[]> doInRedis(RedisConnection connection)
					throws DataAccessException {
				Set<byte[]> returnValue = new HashSet<byte[]>();
				returnValue = connection.keys(getBytesKey(pattern));
				return returnValue;
			}
		});
	}
	
	/**
	 * 获取dbSize
	 * @return
	 */
    public long dbSize(){
    	return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long returnValue = 0;
				returnValue = connection.dbSize();
				return returnValue;
			}
		});
    }

	/**
	 * 获取byte[]类型Key
	 * @param key
	 * @return
	 */
	protected static byte[] getBytesKey(Object object){
		if(object instanceof String){
    		try {
				return ((String) object).getBytes("CHARSET");
			} catch (UnsupportedEncodingException e) {
				return ((String) object).getBytes();
			}
    	}else{
    		return ObjectUtils.serialize(object);
    	}
	}
	/**
	 * Object转换byte[]类型
	 * @param key
	 * @return
	 */
	protected  static byte[] toBytes(Object object){
    	return ObjectUtils.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * @param key
	 * @return
	 */
	protected static Object toObject(byte[] bytes){
		return ObjectUtils.unserialize(bytes);
	}
}
