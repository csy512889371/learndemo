package com.ldap.restservice;

import com.ldap.model.rest.OURequest;

public interface OURestService {
	 public String addOU(OURequest oURequest);
	 public String delOU(String rdn);
}
