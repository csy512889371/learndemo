package redis.core;

/**
 * redis 对象操作  包含string
 *
 * Date:2016年11月4日 下午1:46:06
 * Version:1.0
 */
public interface RedisObjectClient {
	public <T> void set(final byte[] key, final T obj);
	public <T> void set(final byte[] key, final T obj, final long seconds);
	public <T> T get(final byte[] key);
	public <T> T get(final byte[] key, final long seconds);
	public void setString(final byte[] key, final String value, final long seconds);
	public void setString(final byte[] key, final String value);
	public <T> T mGet(final byte[] key);
}
