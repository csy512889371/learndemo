package com.ctoedu.demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.ctoedu.common.repository.BaseRepositoryImpl;

@PropertySource("classpath:${profiles.activation}/jpa.properties")
@EnableJpaRepositories(basePackages="com.ctoedu.demo.**.repository",repositoryBaseClass=BaseRepositoryImpl.class,
repositoryImplementationPostfix="Impl",entityManagerFactoryRef="entityManagerFactory",transactionManagerRef="transactionManager")
public class PersistenceConfig {
	@Autowired
	private Environment env;
	
//	@Bean
//	public PlatformTransactionManager transactionManager(TransactionAwareDataSourceProxy transactionAwareDataSourceProxy)
//	{
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory(transactionAwareDataSourceProxy).getObject());
//        return transactionManager;
//	}
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(TransactionAwareDataSourceProxy transactionAwareDataSourceProxy) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(env.getProperty("jpa.database")));
        jpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(env.getProperty("jpa.generateDdl")));
        jpaVendorAdapter.setShowSql(Boolean.parseBoolean(env.getProperty("jpa.showSql")));
        jpaVendorAdapter.setDatabasePlatform(env.getProperty("jpa.databasePlatform"));
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(transactionAwareDataSourceProxy);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan(env.getProperty("jpa.packagesToScan"));
        emf.setPersistenceUnitName(env.getProperty("jpa.persistenceUnitName"));
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaDialect(new HibernateJpaDialect());
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.validation.mode", env.getProperty("javax.persistence.validation.mode"));
		jpaProperties.put("hibernate.archive.autodetection", env.getProperty("hibernate.archive.autodetection"));
		jpaProperties.put("hibernate.query.startup_check", env.getProperty("hibernate.query.startup_check"));
		jpaProperties.put("hibernate.bytecode.use_reflection_optimizer", env.getProperty("hibernate.bytecode.use_reflection_optimizer"));
		jpaProperties.put("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", env.getProperty("hibernate.enable_lazy_load_no_trans"));
		jpaProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
		emf.setJpaProperties(jpaProperties);
		emf.afterPropertiesSet();
		emf.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return emf;
    }
	@Bean
	@Autowired
	public TransactionAwareDataSourceProxy transactionAwareDataSourceProxy(DataSource dataSource)
	{
		TransactionAwareDataSourceProxy transactionAwareDataSourceProxy = new TransactionAwareDataSourceProxy();
		transactionAwareDataSourceProxy.setTargetDataSource(dataSource);
		return transactionAwareDataSourceProxy;
	}
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean1(TransactionAwareDataSourceProxy transactionAwareDataSourceProxy)
	{
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("com.ctoedu.common.repository.RepositoryHelper.setEntityManagerFactory");
		methodInvokingFactoryBean.setArguments(new Object[]{entityManagerFactory(transactionAwareDataSourceProxy).getObject()});
		return methodInvokingFactoryBean;
	}
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean2()
	{
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("com.ctoedu.common.model.search.utils.SearchableConvertUtils.setConversionService");
		methodInvokingFactoryBean.setArguments(new Object[]{formattingConversionServiceFactoryBean()});
		return methodInvokingFactoryBean;
	}
	@Bean
	public ConversionService formattingConversionServiceFactoryBean()
	{
		return new FormattingConversionServiceFactoryBean().getObject();
	}
	
}
