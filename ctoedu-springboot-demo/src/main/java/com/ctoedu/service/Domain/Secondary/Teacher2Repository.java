package com.ctoedu.service.Domain.Secondary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Teacher2Repository  extends JpaRepository<Teacher2,Integer>{
  //可以用对应的命名规则去写一些特殊的方法
// Teacher findByName(String name);
}
