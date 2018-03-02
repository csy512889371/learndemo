package com.ctoedu.demo.facade.log.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;

/**
 * Created by Administrator on 2017/9/25.
 */
@Entity
@Table(name="UMS_LOG")
public class UmsLog extends UUIDEntity<String> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6439182956220051933L;
	/**
     * 应用系统标识符
     */
    @Column(name="APP_SN")
    private String appSn;
    /**
     * 日志级别
     */
    @Column(name="LOG_LEVEL")
    private Short logLevel;
    /**
     * 日志类型
     */
    @Column(name="LOG_TYPE")
    private Short logType;
    /**
     * 操作用户
     */
    private String username;
    /**
     * 操作类型
     */
    @Column(name="OP_TYPE")
    private Short opType;
    /**
     * 操作结果
     */
    @Column(name="OP_RESULT")
    private Short opResult;
    /**
     * 操作描述
     */
    @Column(name="OP_DESC")
    private String opDesc;
    /**
     * 操作系统
     */
    @Column(name="OP_SYSTEM")
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
    @Column(name="OP_TIME")
    private LocalDateTime opTime;
    /**
     * 前端访问路径
     */
    @Column(name="FRONT_END_ACCESS_PATH")
    private String frontEndAccessPath;
    /**
     * 后端访问路径
     */
    @Column(name="BACK_END_ACCESS_PATH")
    private String backEndAccessPath;
    /**
     * 执行时间
     */
    @Column(name="EXEC_TIME")
    private Long execTime;
    /**
     * 操作模块
     */
    @Column(name="OP_RESOURCE")
    private String opResource;

    public String getAppSn() {
        return appSn;
    }

    public void setAppSn(String appSn) {
        this.appSn = appSn;
    }

    public Short getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Short logLevel) {
        this.logLevel = logLevel;
    }

    public Short getLogType() {
        return logType;
    }

    public void setLogType(Short logType) {
        this.logType = logType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Short getOpType() {
        return opType;
    }

    public void setOpType(Short opType) {
        this.opType = opType;
    }

    public Short getOpResult() {
        return opResult;
    }

    public void setOpResult(Short opResult) {
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

    public LocalDateTime getOpTime() {
        return opTime;
    }

    public void setOpTime(LocalDateTime opTime) {
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

    public Long getExecTime() {
        return execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }

    public String getOpResource() {
        return opResource;
    }

    public void setOpResource(String opResource) {
        this.opResource = opResource;
    }
}
