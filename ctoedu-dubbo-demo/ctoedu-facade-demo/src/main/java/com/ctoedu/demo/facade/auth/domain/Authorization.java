package com.ctoedu.demo.facade.auth.domain;

import java.io.Serializable;

import com.ctoedu.demo.facade.user.entity.UmsUser;

public class Authorization implements Serializable {
	
	private static final long serialVersionUID = 8163063043281407701L;

	private int status;
	
	private UmsUser user;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UmsUser getUser() {
		return user;
	}

	public void setUser(UmsUser user) {
		this.user = user;
	}
}