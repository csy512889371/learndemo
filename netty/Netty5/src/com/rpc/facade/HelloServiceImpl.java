package com.rpc.facade;

import com.rpc.facade.HelloService;

/**
 * HelloServiceImpl
 * 
 *
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "Hello " + name;
    }
}