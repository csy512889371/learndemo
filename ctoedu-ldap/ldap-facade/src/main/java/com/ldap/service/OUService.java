package com.ldap.service;

import com.ldap.model.rest.OURequest;

public interface OUService {
   public String addOU(OURequest oURequest);
   public String delOU(String rdn);
}
