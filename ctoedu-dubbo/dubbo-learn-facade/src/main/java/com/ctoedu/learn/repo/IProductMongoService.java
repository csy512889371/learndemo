package com.ctoedu.learn.repo;


import com.ctoedu.learn.jpa.domain.Product;

public interface IProductMongoService {
    public Product getProductById(int productId);
    public void insertProduct(Product product);
    public void deleteProduct(int productId);
}
