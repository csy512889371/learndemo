package com.ctoedu.demo.facade.resource.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;

@Entity
@Table(name="UMS_BUTTON")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsButton extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	
	@Column(name="BUTTON_NAME")
	private String buttonName;
	
	@Column(name="BUTTON_ICON")
	private String buttonIcon;
	
	@Column(name="BUTTON_EVENT")
	private String buttonEvent;
	
	@Column(name="BUTTON_ORDER")
	private Integer buttonOrder;
	
	private String description;
	
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	
	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getButtonIcon() {
		return buttonIcon;
	}

	public void setButtonIcon(String buttonIcon) {
		this.buttonIcon = buttonIcon;
	}

	public String getButtonEvent() {
		return buttonEvent;
	}

	public void setButtonEvent(String buttonEvent) {
		this.buttonEvent = buttonEvent;
	}

	public Integer getButtonOrder() {
		return buttonOrder;
	}

	public void setButtonOrder(Integer buttonOrder) {
		this.buttonOrder = buttonOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
