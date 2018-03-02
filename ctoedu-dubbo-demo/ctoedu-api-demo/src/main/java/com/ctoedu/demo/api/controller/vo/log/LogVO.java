package com.ctoedu.demo.api.controller.vo.log;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import com.ctoedu.common.utils.DateUtil;
import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import com.ctoedu.demo.facade.log.enums.LogLevelEnum;
import com.ctoedu.demo.facade.log.enums.LogTypeEnum;
import com.ctoedu.demo.facade.log.enums.OpResultEnum;
import com.ctoedu.demo.facade.log.enums.OpTypeEnum;

public class LogVO implements BaseVO {
	
	private String id;
    /**
     * 应用系统
     */
    private String app;
    /**
     * 日志级别
     */
    private String logLevel;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 操作用户
     */
    private String username;
    /**
     * 操作类型
     */
    private String opType;
    /**
     * 操作结果
     */
    private String opResult;
    /**
     * 操作描述
     */
    private String opDesc;
    /**
     * 操作系统
     */
    private String opSystem;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 操作IP地址
     */
    private String ip;
    /**
     * 操作时间
     */
    private String opTime;
    /**
     * 前端访问路径
     */
    private String frontEndAccessPath;
    /**
     * 后端访问路径
     */
    private String backEndAccessPath;
    /**
     * 执行时间
     */
    private String execTime;
    /**
     * 操作模块
     */
    private String opResource;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			UmsLog log = null;
			if(poObj instanceof UmsLog){
				log = (UmsLog)poObj;
			}
			if(log != null){
				this.id = log.getId();
				this.app = log.getAppSn();
				this.logLevel = LogLevelEnum.getInfo(log.getLogLevel());
				this.logType = LogTypeEnum.getInfo(log.getLogType());
				this.username = log.getUsername();
				this.opType = OpTypeEnum.getInfo(log.getOpType());
				this.opResult = OpResultEnum.getInfo(log.getOpResult());
				this.opDesc = log.getOpDesc();
				this.opSystem = log.getOpSystem();
				this.browser = log.getBrowser();
				this.ip = log.getIp();
				if(log.getOpTime()!=null)
				this.opTime = log.getOpTime().format(DateTimeFormatter.ofPattern(DateUtil.DATE_FORMATS[0]));
				this.frontEndAccessPath = log.getFrontEndAccessPath();
				this.backEndAccessPath = log.getBackEndAccessPath();
				Long et = log.getExecTime();
				if(et != null){
					DecimalFormat df = new DecimalFormat("#0.00");
					this.execTime = df.format(et/1000d)+"s";
				}
				this.opResource = log.getOpResource();
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getOpResult() {
		return opResult;
	}

	public void setOpResult(String opResult) {
		this.opResult = opResult;
	}

	public String getOpDesc() {
		return opDesc;
	}

	public void setOpDesc(String opDesc) {
		this.opDesc = opDesc;
	}

	public String getOpSystem() {
		return opSystem;
	}

	public void setOpSystem(String opSystem) {
		this.opSystem = opSystem;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public String getFrontEndAccessPath() {
		return frontEndAccessPath;
	}

	public void setFrontEndAccessPath(String frontEndAccessPath) {
		this.frontEndAccessPath = frontEndAccessPath;
	}

	public String getBackEndAccessPath() {
		return backEndAccessPath;
	}

	public void setBackEndAccessPath(String backEndAccessPath) {
		this.backEndAccessPath = backEndAccessPath;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public String getOpResource() {
		return opResource;
	}

	public void setOpResource(String opResource) {
		this.opResource = opResource;
	}
}