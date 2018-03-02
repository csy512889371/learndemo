package com.ctoedu.demo.facade.jwt.domain;

/**
 * JWT签证
 * @author feichongzheng
 *
 */
public class Signature {
	
	private Header header;
	
	private Payload payload;
	
	/**
	 * secret是保存在服务器端的
	 * jwt的签发生成也是在服务器端的
	 * secret就是用来进行jwt的签发和jwt的验证
	 * 所以，它就是你服务端的私钥
	 * 在任何场景都不应该流露出去
	 * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了
	 */
	private String secret;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}