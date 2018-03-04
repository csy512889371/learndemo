package com.oauth.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public class MyAccessDecisionManager implements AccessDecisionManager {  
	  
    @Override  
    public void decide(Authentication authentication, Object object,  
            Collection<ConfigAttribute> configAttributes)  
            throws AccessDeniedException, InsufficientAuthenticationException {  
//        if( configAttributes == null ) {  
//            return;  
//        }  
//          
//        Iterator<ConfigAttribute> ite = configAttributes.iterator();  
//          
//        while( ite.hasNext()){  
//            ConfigAttribute ca = ite.next();  
//            String needRole = ((SecurityConfig)ca).getAttribute();  
//            for( GrantedAuthority ga: authentication.getAuthorities()){  
//                if(needRole.trim().equals(ga.getAuthority().trim())){  
//                    return;  
//                }  
//            }  
//        }  
//        throw new AccessDeniedException("无权限！");  
  
    }  
  
    @Override  
    public boolean supports(ConfigAttribute attribute) {  
        return true;  
    }  
  
    @Override  
    public boolean supports(Class<?> clazz) {  
        return true;  
    }  
  
}  