package com.ldap.model.mapper;

import com.ldap.model.rest.OUHierarchyResponse;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class HierarchyAttributeMapper implements AttributesMapper{  
	  
    public Object mapFromAttributes(Attributes attr) throws NamingException {
    	OUHierarchyResponse person = new OUHierarchyResponse();  
       
        if (attr.get("sn") != null) {  
            person.setSn((String)attr.get("sn").get());  
        }  
        if (attr.get("cn") != null) {  
            person.setCn((String)attr.get("cn").get());  
        }
        return person;  
    }  
  
}  
