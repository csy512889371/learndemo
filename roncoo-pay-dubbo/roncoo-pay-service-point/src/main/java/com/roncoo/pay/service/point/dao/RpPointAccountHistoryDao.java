/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.point.dao;

import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.service.point.entity.RpPointAccountHistory;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public interface RpPointAccountHistoryDao extends BaseDao<RpPointAccountHistory> {

    /** 根据请求号获取账户历史 **/
    public RpPointAccountHistory getByRequestNo(String requestNo);
}