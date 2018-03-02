package com.ctoedu.demo.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ctoedu.demo.cache.MixCacheManager;

import redis.clients.jedis.JedisPoolConfig;
@PropertySource({"classpath:${profiles.activation}/cache.properties"})
@EnableCaching
public class CacheConfig{
	@Autowired
	private Environment env;
	
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean()
	{
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(env.getProperty("ehcache.configFile")));
		ehCacheManagerFactoryBean.setShared(Boolean.valueOf(env.getProperty("ehcache.shared")));
		return ehCacheManagerFactoryBean;
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public MixCacheManager mixCacheManager(RedisTemplate redisTemplate)
	{
		MixCacheManager mixCacheManager = new MixCacheManager();
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		redisCacheManager.setDefaultExpiration(Long.valueOf(env.getProperty("redis.cache.defaultExpiration")));
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
		mixCacheManager.setEhCacheManager(ehCacheCacheManager);
		mixCacheManager.setRedisCacheManager(redisCacheManager);
		mixCacheManager.setRedisPrefix(env.getProperty("cacheManager.redisPrefix"));
		return mixCacheManager;
	}
	
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration()
	{
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		Set<RedisNode> nodes = new HashSet<RedisNode>();
		RedisClusterNode node1 = new RedisClusterNode(env.getProperty("redis.cluster.node1.host"),Integer.valueOf(env.getProperty("redis.cluster.node1.port")));
		RedisClusterNode node2 = new RedisClusterNode(env.getProperty("redis.cluster.node2.host"),Integer.valueOf(env.getProperty("redis.cluster.node2.port")));
		RedisClusterNode node3 = new RedisClusterNode(env.getProperty("redis.cluster.node3.host"),Integer.valueOf(env.getProperty("redis.cluster.node3.port")));
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		redisClusterConfiguration.setMaxRedirects(Integer.valueOf(env.getProperty("redis.maxRedirects")));
		redisClusterConfiguration.setClusterNodes(nodes);
		return redisClusterConfiguration;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig()
	{
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(Integer.valueOf(env.getProperty("redis.pool.maxActive")));
		jedisPoolConfig.setMinIdle(Integer.valueOf(env.getProperty("redis.pool.minIdle")));
		jedisPoolConfig.setMaxIdle(Integer.valueOf(env.getProperty("redis.pool.maxIdle")));
		jedisPoolConfig.setMaxWaitMillis(Long.valueOf(env.getProperty("redis.pool.maxWait")));
		jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(env.getProperty("redis.pool.testOnBorrow")));
		jedisPoolConfig.setTestOnReturn(Boolean.valueOf(env.getProperty("redis.pool.testOnReturn")));
		return jedisPoolConfig;
	}
	
	@Bean(destroyMethod = "destroy")
	public JedisConnectionFactory jedisConnectionFactory()
	{
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration(),jedisPoolConfig());
		return jedisConnectionFactory;
	}
	
	@Bean
	public StringRedisSerializer stringRedisSerializer()
	{
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		return stringRedisSerializer;
	}
	
	@Bean
	public JdkSerializationRedisSerializer jdkSerializationRedisSerializer()
	{
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		return jdkSerializationRedisSerializer;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory)
	{
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}
}
