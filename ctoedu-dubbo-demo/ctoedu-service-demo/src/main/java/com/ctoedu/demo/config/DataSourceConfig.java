package com.ctoedu.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

@PropertySource("classpath:${profiles.activation}/db.properties")
public class DataSourceConfig {
	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource()
	{
		DruidDataSource dataSource = new DruidDataSource();
		//数据库连接配置
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
        //配置初始化大小、最小、最大
		dataSource.setInitialSize(Integer.valueOf(env.getProperty("druid.initialSize")));
		dataSource.setMinIdle(Integer.valueOf(env.getProperty("druid.minIdle")));
		dataSource.setMaxActive(Integer.valueOf(env.getProperty("druid.maxActive")));
		//配置获取连接等待超时的时间
		dataSource.setMaxWait(Long.valueOf(env.getProperty("druid.maxWait")));
		//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("druid.timeBetweenEvictionRunsMillis")));
		//配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("druid.minEvictableIdleTimeMillis")));
		dataSource.setValidationQuery(env.getProperty("druid.validationQuery"));
		dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("druid.testWhileIdle")));
		dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("druid.testOnBorrow")));
		dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("druid.testOnReturn")));
		return dataSource;
	}
	
}
