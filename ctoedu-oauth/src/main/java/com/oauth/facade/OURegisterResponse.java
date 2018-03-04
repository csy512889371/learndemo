package com.oauth.facade;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * OuPerson
 *
 *
 */
@Data
public class OURegisterResponse implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2573653442241386778L;
    
	
	@JsonProperty("login_name")
	public String login_name;

	@JsonProperty("user_name")
	public String user_name;
	
	@JsonProperty("user_password")
	public String user_password;
	
	@JsonProperty("mobile")
	public String mobile;
	
	@JsonProperty("company")
	public String company;
	
	@JsonProperty("region_name")
	public String region_name;
	
	@JsonProperty("department")
	public String department;
	
	@JsonProperty("employee_type")
	public String employee_type;
}
