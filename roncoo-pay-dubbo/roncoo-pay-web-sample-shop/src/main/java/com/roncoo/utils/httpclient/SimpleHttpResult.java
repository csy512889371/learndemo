/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.utils.httpclient;

import java.util.List;
import java.util.Map;

/**
 * @version:   
 */
public class SimpleHttpResult {
	
	
	public SimpleHttpResult(int code){
		this.statusCode = code;
	}
	
	public SimpleHttpResult(int code, String _content){
		this.statusCode = code;
		this.content = _content;
	}
	
	public SimpleHttpResult(Exception e){
		if(e==null){
			throw new IllegalArgumentException("exception must be specified");
		}
		this.statusCode = -1;
		this.exception = e;
		this.exceptionMsg = e.getMessage();
	}
	/**
	 * HTTP状态码
	 */
	private int statusCode;
	
	/**
	 * HTTP结果
	 */
	private String content;
	
	private String exceptionMsg;
	
	private Exception exception;
	
	private Map<String,List<String>> headers;
	
	private String contentType;
	
	
	public String getHeaderField(String key){
		if(headers==null){
			return null;
		}
		List<String> headerValues = headers.get(key);
		if(headerValues==null || headerValues.isEmpty()){
			return null;
		}
		return headerValues.get(headerValues.size()-1);
	}
	
	public String getContentType(){
		return contentType;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}


	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}
	
	public Exception getException() {
		return exception;
	}

	public boolean isSuccess(){
		return statusCode==200;
	}
	
	public boolean isError(){
		return exception!=null;
	}
	

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
