package com.ctoedu.learn.restservice;


import com.ctoedu.learn.jpa.domain.Product;

public interface IProductMongoRestService {
	  public Product getProductById(int productId);
	  public void insertProduct(Product product);
	  public void deleteProduct(int productId);
}
