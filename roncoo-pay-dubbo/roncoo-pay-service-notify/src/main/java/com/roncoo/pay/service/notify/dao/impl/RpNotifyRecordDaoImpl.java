/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.notify.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.service.notify.dao.RpNotifyRecordDao;
import com.roncoo.pay.service.notify.entity.RpNotifyRecord;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  下午2:31
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Repository("rpNotifyRecordDao")
public class RpNotifyRecordDaoImpl extends BaseDaoImpl<RpNotifyRecord> implements RpNotifyRecordDao {


    @Override
    public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        paramMap.put("notifyType",notifyType);

        return super.getBy(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public RpNotifyRecord selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKey(RpNotifyRecord record) {
        return 0;
    }

}
