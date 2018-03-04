package com.ldap.model.rest;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Data;

/**
 * organization
 *
 *
 */
@Data
public class OrganizationRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 748586917532616971L;
    
	/**
	 * o
	 */
	@JsonProperty("organization_name")
	private String organizationName;
	
	@JsonProperty("domain_name")
	private String domainName;
}
