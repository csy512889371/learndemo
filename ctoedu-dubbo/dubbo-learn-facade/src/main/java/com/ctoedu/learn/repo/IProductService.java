package com.ctoedu.learn.repo;


import com.ctoedu.learn.mybatis.domain.Product;

public interface IProductService {
    public Product getProductById(int productId);
    public void insertProduct(Product product);
    public void deleteProduct(int productId);
}
