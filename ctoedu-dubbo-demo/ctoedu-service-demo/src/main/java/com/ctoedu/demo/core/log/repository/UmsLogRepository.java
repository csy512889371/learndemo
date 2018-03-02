package com.ctoedu.demo.core.log.repository;

import org.springframework.stereotype.Repository;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.log.entity.UmsLog;

/**
 * Created by Administrator on 2017/9/25.
 */
@Repository
public interface UmsLogRepository extends BaseRepository<UmsLog,String> {
}
