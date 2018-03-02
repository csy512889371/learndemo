package redis.core;

import java.util.List;

/**
 *
 * Date:2016年11月4日 下午1:45:33
 * Version:1.0
 */
public interface RedisListClient {

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 * @param key
	 * @param seconds 0 为永不过期
	 * @param values
	 * @return 执行 LPUSH 命令后，列表的长度。
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long lpush(String key, int seconds, T... values);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头 为永不过期
	 * @param key
	 * @param values
	 * @return 执行 LPUSH 命令后，列表的长度。
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long lpush(String key, T... values);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾
	 * @param key
	 * @param seconds 0 为永不过期
	 * @param values
	 * @return 执行 LPUSH 命令后，列表的长度。
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long rpush(String key, int seconds, T... values);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾 为永不过期
	 * @param key
	 * @param values
	 * @return 执行 LPUSH 命令后，列表的长度。
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> long rpush(String key, T... values);

	/**
	 * 取出list
	 * @param key
	 * @param begin
	 * @param end -1 表示倒数第一以此类推
	 * @return
	 */
	public abstract <T> List<T> lrange(String key, int begin, int end);

}
