package com.ctoedu.learn.repo;


import com.ctoedu.learn.jpa.domain.Enterprise;

public interface IEnterpriseMongoService {
     public Enterprise getEnterpriseById(int enterpriseId);
     public void insertEnterprise(Enterprise enterprise);
     public void deleteEnterprise(int enterpriseId);
}
