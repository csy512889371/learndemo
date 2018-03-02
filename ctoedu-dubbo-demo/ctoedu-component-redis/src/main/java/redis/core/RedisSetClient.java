package redis.core;

import java.util.Set;

/**
 *
 * Date:2016年11月4日 下午1:46:50
 * Version:1.0
 */
public interface RedisSetClient {

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
			假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * @param key
	 * @param seconds 0 永不过期
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long sadd(String key, int seconds, T... values);

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
			假如 key 不存在，则创建一个只包含 member 元素作成员的集合。 永不过期
	 * @param key
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long sadd(String key, T... values);

	/**
	 * 返回所有set
	 * @param key
	 * @return  key 不存在将返回空的set
	 */
	public abstract <T> Set<T> smembers(String key);

}
