/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.point.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明： 账户资金变动方向
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public enum PointAccountHistoryStatusEnum {

	/**
	 * 预处理阶段
	 */
	TRYING("处理中"),

	/**
	 * 已确认的
	 */
	CONFORM("已确认"),

	/**
	 * 取消的
	 */
	CANCEL("取消");


	/** 描述 */
	private String label;

	private PointAccountHistoryStatusEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	public static List<Map<String, Object>> getList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PointAccountHistoryStatusEnum[] val = PointAccountHistoryStatusEnum.values();
		for (PointAccountHistoryStatusEnum e : val) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", e.getLabel());
			map.put("name", e.name());
			list.add(map);
		}
		return list;
	}

	public static PointAccountHistoryStatusEnum getEnum(String name) {
		PointAccountHistoryStatusEnum resultEnum = null;
		PointAccountHistoryStatusEnum[] enumAry = PointAccountHistoryStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(name)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

}
