package com.itmuch.cloud.feign;

public class FeignClient2Fallback implements FeignClient2 {

  @Override
  public String findServiceInfoFromEurekaByServiceName(String serviceName) {
    return "haha";
  }

}
