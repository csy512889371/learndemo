package com.ctoedu.learn.mybatis.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Product implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("product_id")
	private Integer id;
	
	@JsonProperty("enterprise_id")
    private Integer enterpriseid;
	
	@JsonProperty("product_name")
    private String productname;

	@JsonProperty("product_info")
    private String productinfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnterpriseid() {
        return enterpriseid;
    }

    public void setEnterpriseid(Integer enterpriseid) {
        this.enterpriseid = enterpriseid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo == null ? null : productinfo.trim();
    }
}