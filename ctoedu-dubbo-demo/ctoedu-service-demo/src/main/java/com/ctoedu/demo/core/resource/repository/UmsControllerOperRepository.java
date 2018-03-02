package com.ctoedu.demo.core.resource.repository;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.resource.entity.UmsControllerOper;

public interface UmsControllerOperRepository extends BaseRepository<UmsControllerOper,String> {
	public UmsControllerOper findByControllerOperSn(String controllerOperSn);
}
