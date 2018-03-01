package com.ctoedu.learn.repo.Impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ctoedu.learn.jpa.repo.ProductRepo;
import com.ctoedu.learn.jpa.domain.Product;
import com.ctoedu.learn.repo.IProductMongoService;


@Service("productMongoService")
public class ProductMongoServiceImpl implements IProductMongoService {
  

	@Autowired
	ProductRepo productRepo;

	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return productRepo.findOne(productId);
	}

	public void insertProduct(Product product) {
		// TODO Auto-generated method stub
		productRepo.save(product);
	}

	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		productRepo.delete(productId);
	}

}
