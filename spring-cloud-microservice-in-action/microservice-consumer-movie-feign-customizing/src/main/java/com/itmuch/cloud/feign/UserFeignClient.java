package com.itmuch.cloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.itmuch.cloud.entity.User;
import com.itmuch.config.Configuration1;

import feign.Param;
import feign.RequestLine;

@FeignClient(name = "microservice-provider-user", configuration = Configuration1.class)
public interface UserFeignClient {
  @RequestLine("GET /simple/{id}")
  public User findById(@Param("id") Long id);
}
