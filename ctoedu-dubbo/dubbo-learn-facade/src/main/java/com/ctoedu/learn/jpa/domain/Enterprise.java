package com.ctoedu.learn.jpa.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection="enterprise")
@Data
public class Enterprise implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Field("id")
	@JsonProperty("enterprise_id")
	private Integer id;

	@Field("name")
	@JsonProperty("enterprise_name")
    private String name;

	@Field("address")
	@JsonProperty("enterprise_address")
    private String address;
}