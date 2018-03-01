package com.ctoedu.learn.repo.Impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.learn.jpa.repo.EnterpriseRepo;
import com.ctoedu.learn.jpa.domain.Enterprise;
import com.ctoedu.learn.repo.IEnterpriseMongoService;



@Service("enterpriseMongoService")
public class EnterpriseMongoServiceImpl implements IEnterpriseMongoService{
   
	@Autowired
	EnterpriseRepo enterpriseRepo;

	public Enterprise getEnterpriseById(int enterpriseId) {
		// TODO Auto-generated method stub
		return enterpriseRepo.findOne(enterpriseId);
	}

	public void insertEnterprise(Enterprise enterprise) {
		// TODO Auto-generated method stub
		enterpriseRepo.save(enterprise);
	}

	public void deleteEnterprise(int enterpriseId) {
		// TODO Auto-generated method stub
		enterpriseRepo.delete(enterpriseId);
	}



}
