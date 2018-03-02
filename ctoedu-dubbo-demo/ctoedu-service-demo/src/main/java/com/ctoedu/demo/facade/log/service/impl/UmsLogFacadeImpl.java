package com.ctoedu.demo.facade.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.log.service.UmsLogService;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import com.ctoedu.demo.facade.log.service.UmsLogFacade;

/**
 * Created by Administrator on 2017/9/25.
 */
@Service(retries=2,interfaceName="com.ctoedu.demo.facade.log.service.UmsLogFacade")
public class UmsLogFacadeImpl implements UmsLogFacade {
    @Autowired
    private UmsLogService umsLogService;
    @Override
    public UmsLog save(UmsLog umsLog) {
        return umsLogService.save(umsLog);
    }

    @Override
    public Page<UmsLog> listPage(Searchable searchable) {
        return umsLogService.findAll(searchable);
    }

    @Override
    public List<UmsLog> list(Searchable searchable) {
        return umsLogService.findAllWithNoPageNoSort(searchable);
    }
}
