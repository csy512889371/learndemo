package com.ldap.model.rest;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Data;

/**
 * OrganizationalUnit
 *
 *
 */
@Data
public class OURequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 9024750821710756080L;
    
	@JsonProperty("rdn")
	public String rdn;
	
	@JsonProperty("ou_name")
	public String oUName;
}
