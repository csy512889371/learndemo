package com.ctoedu.demo.facade.jwt.domain;

import java.io.Serializable;

/**
 * JWT载荷
 * @author feichongzheng
 *
 */
public class Payload implements Serializable {
	
	private static final long serialVersionUID = 4549974315128750465L;

	private String name;//用户名
	
	private String iss;//jwt签发者
	
	private String sub;//jwt所面向的用户
	
	private String aud;//接收jwt的一方
	
	private long exp;//jwt的过期时间，这个过期时间必须要大于签发时间
	
	private String nbf;//定义在什么时间之前，该jwt都是不可用的
	
	private long iat;//jwt的签发时间
	
	private String jti;//jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public String getNbf() {
		return nbf;
	}

	public void setNbf(String nbf) {
		this.nbf = nbf;
	}

	public long getIat() {
		return iat;
	}

	public void setIat(long iat) {
		this.iat = iat;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}
}