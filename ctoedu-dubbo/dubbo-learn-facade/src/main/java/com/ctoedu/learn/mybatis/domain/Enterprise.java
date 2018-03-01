package com.ctoedu.learn.mybatis.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Enterprise implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("enterprise_id")
	private Integer id;

	@JsonProperty("enterprise_name")
    private String name;

	@JsonProperty("enterprise_address")
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}