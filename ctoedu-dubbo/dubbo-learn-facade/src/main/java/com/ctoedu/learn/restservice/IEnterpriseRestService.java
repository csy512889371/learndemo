package com.ctoedu.learn.restservice;


import com.ctoedu.learn.mybatis.domain.Enterprise;

public interface IEnterpriseRestService {
	  public Enterprise getEnterpriseById(int id);
	  public void insertEnterprise(Enterprise enterprise);
	  public String getString(String name);
	  public void deleteEnterprise(int enterpriseId);
}
