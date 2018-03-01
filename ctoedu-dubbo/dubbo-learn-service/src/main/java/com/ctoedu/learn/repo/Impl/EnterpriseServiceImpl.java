package com.ctoedu.learn.repo.Impl;

import com.ctoedu.learn.mybatis.IDao.EnterpriseMapper;
import com.ctoedu.learn.mybatis.domain.Enterprise;
import com.ctoedu.learn.repo.IEnterpriseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("enterpriseService")
public class EnterpriseServiceImpl implements IEnterpriseService{
	Logger  logger=Logger.getLogger(EnterpriseServiceImpl.class); 
	@Resource
	private  EnterpriseMapper enterpriseMapper;
	
	//根据id查询
	public Enterprise getEnterpriseById(int enterpriseId) {
		return enterpriseMapper.selectByPrimaryKey(enterpriseId);
	}


	public void insertEnterprise(Enterprise enterprise) {
		// TODO Auto-generated method stub
		enterpriseMapper.insert(enterprise);
	}


	public void deleteEnterprise(int enterpriseId) {
		enterpriseMapper.deleteByPrimaryKey(enterpriseId);
	}

}
