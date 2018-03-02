package com.ctoedu.demo.test.base;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ctoedu.common.repository.RepositoryHelper;
import com.ctoedu.demo.config.ContextConfig;

/**
 * Created by et on 2015/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ContextConfig.class)
@Transactional 
@Rollback
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否  
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
public class BaseTest {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    @Before
    public void setUp() {
        RepositoryHelper.setEntityManagerFactory(entityManagerFactory);
    }
    @Test
    public void init(){
    }
}
