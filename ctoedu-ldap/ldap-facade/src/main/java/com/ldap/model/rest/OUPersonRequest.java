package com.ldap.model.rest;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Data;

/**
 * OuPerson
 *
 *
 */
@Data
public class OUPersonRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2573653442241386778L;
    
	@JsonProperty("rdn")
	public String rdn;
	
	@JsonProperty("login_name")
	public String uid;

	@JsonProperty("user_name")
	public String sn;
	
	@JsonProperty("user_password")
	public String password;
	
	@JsonProperty("mobile")
	public String mobile;
	
	@JsonProperty("region_name")
	public String regionName;
	
	@JsonProperty("company")
	public String company;
	
	@JsonProperty("department")
	public String department;
	
	@JsonProperty("employee_type")
	public String employeeType;
}
