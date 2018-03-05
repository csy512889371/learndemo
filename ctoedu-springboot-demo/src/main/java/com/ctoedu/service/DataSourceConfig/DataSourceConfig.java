package com.ctoedu.service.DataSourceConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

   @Bean(name="primaryDataSource")
   @Qualifier("primaryDataSource")
   @ConfigurationProperties(prefix="spring.datasource.primary")
   public DataSource primaryDataSource(){
	   return DataSourceBuilder.create().build();
   }
   
   @Bean(name="seoncodaryDataSource")
   @Qualifier("secondaryDataSource")
   @Primary
   @ConfigurationProperties(prefix="spring.datasource.secondary")
   public DataSource secoundaryDataSource(){
	   return DataSourceBuilder.create().build();
   }
   
   //支持JdbcTemplate实现多数据源
   @Bean(name="primaryJdbcTemplate")
   public JdbcTemplate primaryJdbcTemplate(
		   @Qualifier("primaryDataSource") DataSource dataSource){
	   return new JdbcTemplate(dataSource);
	   }
   @Bean(name="secondaryJdbcTemplate")
   public JdbcTemplate secondaryJdbcTemplate(
		   @Qualifier("secondaryDataSource") DataSource dataSource){
	   return new JdbcTemplate(dataSource);
	   }
   }