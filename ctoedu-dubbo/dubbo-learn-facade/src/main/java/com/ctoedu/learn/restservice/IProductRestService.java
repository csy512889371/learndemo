package com.ctoedu.learn.restservice;


import com.ctoedu.learn.mybatis.domain.Product;

public interface IProductRestService {
	  public Product getProductById(int productId);
	  public void insertProduct(Product product);
	  public void deleteProduct(int productId);
}
