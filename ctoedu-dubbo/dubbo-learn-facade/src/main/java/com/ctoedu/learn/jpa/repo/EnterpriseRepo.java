package com.ctoedu.learn.jpa.repo;

import com.ctoedu.learn.jpa.domain.Enterprise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnterpriseRepo extends MongoRepository<Enterprise,Integer> {

}
