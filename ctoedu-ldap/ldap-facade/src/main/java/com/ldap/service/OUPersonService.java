package com.ldap.service;

import java.util.List;

import com.ldap.model.rest.OUHierarchyRequest;
import com.ldap.model.rest.OUHierarchyResponse;
import com.ldap.model.rest.OUPersonRequest;
import com.ldap.model.rest.OUPersonResponse;
import com.ldap.model.rest.OURegisterRequest;

public interface OUPersonService {
	  public String addOUPerson(OUPersonRequest oUPersonRequest);
	  public String delOUPerson(String rdn);
	  public String addOURegister(OURegisterRequest oURegisterRequest);
	  public OUPersonResponse getOUPerson(String loginName,String userPassword);
	  public String moveOUPerson(String oldrdn,String newrdn);
	  public String addOUHierarchy(OUHierarchyRequest oUHierarchyRequest);
      public String getRdn(String uid);
      public List<OUHierarchyResponse> getNextOUHierarchy(String rdn);
      public String delOUHierarchy(String rdn);
}
