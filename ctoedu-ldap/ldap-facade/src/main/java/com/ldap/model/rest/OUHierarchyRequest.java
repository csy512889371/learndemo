package com.ldap.model.rest;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Data;

@Data
public class OUHierarchyRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 828687925899533133L;
	
	@JsonProperty("rdn")
	public String rdn;
	
	@JsonProperty("login_name")
	public String cn;
	
	@JsonProperty("user_name")
	public String sn;
}
