package cn.ctoedu.kafka.ubas.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactory {
	private static Logger LOG = Logger.getLogger(JedisFactory.class.getName());
	private final static int MAX_ACTIVE = 5000;
	private final static int MAX_IDLE = 800;
	private final static int MAX_WAIT = 10000;
	private final static int TIMEOUT = 10 * 1000;

	private static Map<String, JedisPool> jedisPools = new HashMap<String, JedisPool>();

	public static JedisPool initJedisPool(String jedisName) {
		JedisPool jPool = jedisPools.get(jedisName);
		if (jPool == null) {
			String host = SystemConfig.getProperty(jedisName + ".redis.host");
			int port = SystemConfig.getIntProperty(jedisName + ".redis.port");
			String[] hosts = host.split(",");
			for (int i = 0; i < hosts.length; i++) {
				try {
					jPool = newJeisPool(hosts[i], port);
					if (jPool != null) {
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			jedisPools.put(jedisName, jPool);
		}
		return jPool;
	}

	public static Jedis getJedisInstance(String jedisName) {
		LOG.debug("get jedis[name=" + jedisName + "]");
		JedisPool jedisPool = jedisPools.get(jedisName);
		if (jedisPool == null) {
			jedisPool = initJedisPool(jedisName);
		}

		Jedis jedis = null;
		for (int i = 0; i < 10; i++) {
			try {
				jedis = jedisPool.getResource();
				break;
			} catch (Exception e) {
				LOG.error("get resource from jedis pool error. times " + (i + 1) + ". retry...", e);
				jedisPool.returnBrokenResource(jedis);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					LOG.warn("sleep error", e1);
				}
			}
		}
		return jedis;
	}

	private static JedisPool newJeisPool(String host, int port) {
		LOG.info("init jedis pool[" + host + ":" + port + "]");
		JedisPoolConfig config = new JedisPoolConfig();
		config.setTestOnReturn(false);
		config.setTestOnBorrow(false);
		config.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
		config.setMaxActive(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWait(MAX_WAIT);
		return new JedisPool(config, host, port, TIMEOUT);
	}

	/**
	 * 配合使用getJedisInstance方法后将jedis对象释放回连接池中
	 * 
	 * @param jedis
	 *            使用完毕的Jedis对象
	 * @return true 释放成功；否则返回false
	 */
	public static boolean release(String poolName, Jedis jedis) {
		LOG.debug("release jedis pool[name=" + poolName + "]");

		JedisPool jedisPool = jedisPools.get(poolName);
		if (jedisPool != null && jedis != null) {
			try {
				jedisPool.returnResource(jedis);
			} catch (Exception e) {
				jedisPool.returnBrokenResource(jedis);
			}
			return true;
		}
		return false;
	}

	public static void destroy() {
		LOG.debug("destroy all pool");
		for (Iterator<JedisPool> itors = jedisPools.values().iterator(); itors.hasNext();) {
			try {
				JedisPool jedisPool = itors.next();
				jedisPool.destroy();
			} finally {
			}
		}
	}

	public static void destroy(String poolName) {
		try {
			jedisPools.get(poolName).destroy();
		} catch (Exception e) {
			LOG.warn("destory redis pool[" + poolName + "] error", e);
		}
	}
}
