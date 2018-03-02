package com.ctoedu.demo.facade.resource.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.app.entity.UmsApp;

@Entity
@Table(name = "UMS_CONTROLLER_RESOURCES")
public class UmsControllerResources extends UUIDEntity<String> implements Resource,Comparable<UmsControllerResources>{
	private static final long serialVersionUID = 1L;

	@Column(name="CONTROLLER_NAME")
	private String controllerName;
	@Column(name="CONTROLLER_SN")
	private String controllerSn;
	@Column(name="CONTROLLER_URL_MAPPING")
	private String controllerUrlMapping;
	@Column(name="CONTROLLER_ORDER")
	private Integer controllerOrder;
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private UmsControllerResources parent;
	@ManyToOne
	@JoinColumn(name="MENU_ID")
	private UmsMenuResources menu;
	@ManyToOne
	@JoinColumn(name="APPLICATION_ID")
	private UmsApp application;
	@OneToMany(cascade = { CascadeType.ALL },mappedBy="controllerResources",fetch=FetchType.EAGER)
	private Set<UmsControllerOper> umsControllerOpers;
	
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public String getControllerSn() {
		return controllerSn;
	}
	public void setControllerSn(String controllerSn) {
		this.controllerSn = controllerSn;
	}
	public String getControllerUrlMapping() {
		return controllerUrlMapping;
	}
	public void setControllerUrlMapping(String controllerUrlMapping) {
		this.controllerUrlMapping = controllerUrlMapping;
	}
	public Integer getControllerOrder() {
		return controllerOrder;
	}
	public void setControllerOrder(Integer controllerOrder) {
		this.controllerOrder = controllerOrder;
	}
	public Short getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}
	public UmsApp getApplication() {
		return application;
	}
	public void setApplication(UmsApp application) {
		this.application = application;
	}
	public UmsControllerResources getParent() {
		return parent;
	}
	public void setParent(UmsControllerResources parent) {
		this.parent = parent;
	}
	public Set<UmsControllerOper> getUmsControllerOpers() {
		return umsControllerOpers;
	}
	public void setUmsControllerOpers(Set<UmsControllerOper> umsControllerOpers) {
		this.umsControllerOpers = umsControllerOpers;
	}
	
	public UmsMenuResources getMenu() {
		return menu;
	}
	public void setMenu(UmsMenuResources menu) {
		this.menu = menu;
	}
	@Override
	public int compareTo(UmsControllerResources ucr) {
		if(this.controllerOrder == null && ucr.controllerOrder == null){
			return this.controllerName.compareTo(ucr.controllerName);
		}else if(this.controllerOrder == null){
			return -1;
		}else if(ucr.controllerOrder == null){
			return 1;
		}else{
			int num = this.controllerOrder.compareTo(ucr.controllerOrder);
			return num==0?this.controllerName.compareTo(ucr.controllerName):num;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)  
	        return false;  
	    if(obj == null)  
	        return false;  
	    if(getClass() != obj.getClass() )  
	        return false;  
	    UmsControllerResources other = (UmsControllerResources)obj;  
	    if(this.getId() != other.getId())  
	        return false;  
	    return true;  
	}
}
