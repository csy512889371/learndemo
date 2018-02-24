/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.entity;

import java.io.Serializable;

import com.roncoo.pay.common.core.entity.BaseEntity;

/** 此实体没有关联的表，只作用于序列查找时传参使用 **/
public class SeqBuild extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 序列名称 **/
	private String seqName;

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

}