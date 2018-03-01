package com.ctoedu.learn.repo.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctoedu.learn.mybatis.IDao.ProductMapper;
import com.ctoedu.learn.mybatis.domain.Product;
import com.ctoedu.learn.repo.IProductService;

@Service("productService")
public class ProductServiceImpl implements IProductService {
  
	@Resource
	private ProductMapper productMapper;
	
	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return productMapper.selectByPrimaryKey(productId);
	}

	public void insertProduct(Product product) {
		// TODO Auto-generated method stub
		productMapper.insert(product);
	}

	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		productMapper.deleteByPrimaryKey(productId);
	}

}
