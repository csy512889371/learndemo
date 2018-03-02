package com.ctoedu.demo.facade.resource.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
@Entity
@Table(name = "UMS_CONTROLLER_OPER")
public class UmsControllerOper extends UUIDEntity<String> {

	private static final long serialVersionUID = 1L;
	@Column(name="CONTROLLER_OPER_SN")
	private String controllerOperSn;
	@Column(name="METHOD_URL_MAPPING")
	private String methodUrlMapping;
	@Column(name="CONTROLLER_OPER_NAME")
	private String controllerOperName;
	@Column(name="INDEX_POS")
	private Integer indexPos;
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="CONTROLLER_ID")
	private UmsControllerResources controllerResources;
	public String getControllerOperSn() {
		return controllerOperSn;
	}
	public void setControllerOperSn(String controllerOperSn) {
		this.controllerOperSn = controllerOperSn;
	}
	public String getMethodUrlMapping() {
		return methodUrlMapping;
	}
	public void setMethodUrlMapping(String methodUrlMapping) {
		this.methodUrlMapping = methodUrlMapping;
	}
	public String getControllerOperName() {
		return controllerOperName;
	}
	public void setControllerOperName(String controllerOperName) {
		this.controllerOperName = controllerOperName;
	}
	public Integer getIndexPos() {
		return indexPos;
	}
	public void setIndexPos(Integer indexPos) {
		this.indexPos = indexPos;
	}
	public UmsControllerResources getControllerResources() {
		return controllerResources;
	}
	public void setControllerResources(UmsControllerResources controllerResources) {
		this.controllerResources = controllerResources;
	}

}
