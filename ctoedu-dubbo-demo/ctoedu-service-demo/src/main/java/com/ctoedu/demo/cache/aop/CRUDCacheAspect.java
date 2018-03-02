package com.ctoedu.demo.cache.aop;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import com.ctoedu.common.utils.ReflectUtils;

//@Component
//@Aspect
public class CRUDCacheAspect extends BaseCacheAspect {
    public CRUDCacheAspect() {
        setCacheName("redis-cache");
    }

    private String idKeyPrefix = "id-";
    
    /**
     * 匹配Service
     */
    @Pointcut(value = "target(com.ctoedu.common.service.BaseService)")
    private void servicePointcut() {
    }
    
    /**
     * only put
     * 如 新增 修改
     */
    @Pointcut(value ="execution(* save(..)) " +
    				"|| execution(* saveAndFlush(..)) " +
                    "|| execution(* update(..)) ")
    private void cachePutPointcut() {
    }


    /**
     * evict 比如删除
     */
    @Pointcut(value = "(execution(* delete(*))) && args(arg)", argNames = "arg")
    private void cacheEvictPointcut(Object arg) {
    }
    
    /**
     * get
     * 比如查询
     */
    @Pointcut(value ="(execution(* findOne(*)))")
    private void cacheablePointcut() {
    }
    
    @AfterReturning(value = "servicePointcut() && cachePutPointcut()", returning = "obj")
    public void cachePutAdvice(Object obj) {
        //put cache
        put(idKey(obj),obj);
    }

    @After(value = "servicePointcut() && cacheEvictPointcut(arg)", argNames = "arg")
    public void cacheEvictAdvice(Object obj) {
        if (obj == null) {
            return;
        }
        evict(idKey(obj));
    }

    @Around(value = "servicePointcut() && cacheablePointcut()")
    public Object cacheableAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Object arg = pjp.getArgs().length >= 1 ? pjp.getArgs()[0] : null;
        String idKey = idKey((String)arg,getReturnTypeName(pjp));
        Object obj = get(idKey);
        if (obj != null) {
            log.debug("cacheName:{}, hit key:{}", cacheName, idKey);
            return obj;
        }
        obj = pjp.proceed();
        //put cache
        put(idKey,obj);
        return obj;
    }
    
    @SuppressWarnings("rawtypes")
	private String getReturnTypeName(ProceedingJoinPoint pjp){
    	String returnName = null;
    	Type type = pjp.getTarget().getClass().getGenericSuperclass();
        if (type  instanceof  ParameterizedType) {
            ParameterizedType  paramType  =  (ParameterizedType) type;
            Type[] args = paramType.getActualTypeArguments();
            if (args != null && args.length>0) {
                  Type arg = args[0]; 
                  if (arg instanceof Class) {
                	  returnName = ((Class) arg).getSimpleName();
                  }
            }
        }
        return returnName;
    }
    
    private String idKey(Object obj){
    	StringBuffer idKey = new StringBuffer();
		try {
			idKey.append(obj.getClass().getSimpleName()).append("-").append(idKeyPrefix).append(ReflectUtils.getFieldValue(obj, "id"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
    	return idKey.toString();
    }
    
    private String idKey(String obj,String name){
    	StringBuffer idKey = new StringBuffer();
    	idKey.append(name).append("-").append(idKeyPrefix).append(obj);
    	return idKey.toString();
    }
}
