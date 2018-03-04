package com.ldap.model.mapper;

import com.ldap.model.rest.OUPersonResponse;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

public class PersonAttributeMapper implements AttributesMapper{  
	  
    public Object mapFromAttributes(Attributes attr) throws NamingException {
    	OUPersonResponse person = new OUPersonResponse();  
        person.setUid((String)attr.get("uid").get());  
       
        if (attr.get("userPassword") != null) {  
        	Attribute at=attr.get("userPassword");
        	byte[] bytes=(byte[])at.get();
            person.setPassword(new String(bytes));  
        }  
        if (attr.get("sn") != null) {  
            person.setSn((String)attr.get("sn").get());  
        }  
        if (attr.get("mobile") != null) {  
            person.setMobile((String)attr.get("mobile").get());  
        }
        if(attr.get("o") != null){
        	person.setCompany((String)attr.get("o").get());
        }
        if(attr.get("ou") != null){
        	person.setDepartment((String)attr.get("ou").get());
        }
        if(attr.get("employeeType") != null){
        	person.setEmployeeType((String)attr.get("employeeType").get());
        }
        if(attr.get("l") != null){
        	person.setRegionName((String)attr.get("l").get());
        }
        return person;  
    }  
  
}  
