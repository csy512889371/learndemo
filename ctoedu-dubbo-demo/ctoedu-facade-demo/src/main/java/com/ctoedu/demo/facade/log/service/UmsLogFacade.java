package com.ctoedu.demo.facade.log.service;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public interface UmsLogFacade {
    /**
     * 日志记录
     * @param umsLog
     * @return
     */
    public UmsLog save(UmsLog umsLog);

    /**
     * 分页查询
     * @param searchable
     * @return
     */
    public Page<UmsLog> listPage(Searchable searchable);

    /**
     * 条件查询
     * @param searchable
     * @return
     */
    public List<UmsLog> list(Searchable searchable);
}
