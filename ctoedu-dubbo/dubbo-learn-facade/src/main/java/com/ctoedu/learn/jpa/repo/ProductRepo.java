package com.ctoedu.learn.jpa.repo;

import com.ctoedu.learn.jpa.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends MongoRepository<Product,Integer>{

}