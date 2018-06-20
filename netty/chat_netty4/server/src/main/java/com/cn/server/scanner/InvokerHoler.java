package com.cn.server.scanner;

import java.util.HashMap;
import java.util.Map;
/**
 * 命令执行器管理者
 *
 *
 */
public class InvokerHoler {
	
    /**命令调用器*/
    private static Map<Short, Map<Short, Invoker>> invokers = new HashMap<>();
    
    /**
     * 添加命令调用
     * @param module
     * @param cmd
     * @param invoker
     */
    public static void addInvoker(short module, short cmd, Invoker invoker){
    	Map<Short, Invoker> map = invokers.get(module);
    	if(map == null){
    		map = new HashMap<>();
    		invokers.put(module, map);
    	}
    	map.put(cmd, invoker);
    }
    
    
    /**
     * 获取命令调用
     * @param module
     * @param cmd
     * @param invoker
     */
    public static Invoker getInvoker(short module, short cmd){
    	Map<Short, Invoker> map = invokers.get(module);
    	if(map != null){
    		return map.get(cmd);
    	}
    	return null;
    }

}
