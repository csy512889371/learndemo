package redis.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Date:2016年11月4日 下午1:44:03
 * Version:1.0
 */
public interface RedisHashClient {

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * @param key
	 * @param fields
	 * @return 被成功移除的域的数量，不包括被忽略的域。
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long hdel(String key, T... fields);

	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * @param key
	 * @param field
	 * @return 如果哈希表含有给定域 返回true
	 * 		      如果哈希表不含有给定域，或 key 不存在 返回false
	 */
	public abstract <T> Boolean hexists(String key, T field);

	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param field
	 * @return 当给定域不存在或是给定 key 不存在时 返回null
	 */
	public abstract <T> T hget(String key, T field);

	/**
	 * 返回哈希表 key 中，所有的域和值。
	 * @param key
	 * @return 以列表形式返回哈希表的域和域的值。
	 * 		      若 key 不存在，返回空列表。
	 */
	public abstract <K, V> Map<K, V> hgetAll(String key);

	/**
	 * 返回hash表中所有的key
	 * @param key
	 * @return 当 key 不存在时 返回空表
	 */
	public abstract <T> Set<T> hkeys(String key);

	/**
	 * 返回hash表中所有的值
	 * @param key
	 * @return 当 key 不存在时 返回空表
	 */
	public abstract <T> List<T> hvals(String key);

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
		如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
		如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @param seconds 0 标识不过期
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回true 。
			      如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 false 。
	 */
	public abstract <K, V> Boolean hset(String key, K field, V value,
                                        int seconds);

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
		如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
		如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回true 。
			      如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 false 。
	 */
	public abstract <K, V> Boolean hset(String key, K field, V value);

	/**
	 * 将一组map放入key中
	 * @param key
	 * @param map
	 * @param seconds 0 不过期
	 */
	public abstract <K, V> void hmset(String key, Map<K, V> map, int seconds);

	/**
	 * 永久放入map到key 中
	 * @param key
	 * @param map
	 */
	public abstract <K, V> void hmset(String key, Map<K, V> map);

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。
	 * @param key
	 * @param fields
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public abstract <K, V> List<V> hmget(String key, K... fields);
}