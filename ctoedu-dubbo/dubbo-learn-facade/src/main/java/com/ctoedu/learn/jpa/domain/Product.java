package com.ctoedu.learn.jpa.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection="product")
@Data
public class Product implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field("id")
	@JsonProperty("product_id")
	private Integer id;
	
	@Field("enterpriseid")
	@JsonProperty("enterprise_id")
    private Integer enterpriseid;
	
	@Field("productname")
	@JsonProperty("product_name")
    private String productname;

	@Field("productinfo")
	@JsonProperty("product_info")
    private String productinfo;

   
}