package com.rpc.common;

import com.rpc.facade.HelloService;
import com.rpc.facade.HelloServiceImpl;

/**
 * RpcProvider
 * 
 *
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}