package com.cn.common.core.model;
/**
 * 回复消息
 *
 *
 */
public class Response {
	
	/**
	 * 模块号
	 */
	private short module;
	
	/**
	 * 命令号
	 */
	private short cmd;
	
	/**
	 * 结果码
	 */
	private int stateCode = ResultCode.SUCCESS;
	
	/**
	 * 数据
	 */
	private byte[] data;
	
	public Response() {
	}
	
	public Response(Request message) {
		this.module = message.getModule();
		this.cmd = message.getCmd();
	}
	
	public Response(short module, short cmd, byte[] data){
		this.module = module;
		this.cmd = cmd;
		this.data = data;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public short getModule() {
		return module;
	}

	public void setModule(short module) {
		this.module = module;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}
}
