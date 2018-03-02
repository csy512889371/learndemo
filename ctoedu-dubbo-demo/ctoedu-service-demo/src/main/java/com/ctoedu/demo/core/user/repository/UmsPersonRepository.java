package com.ctoedu.demo.core.user.repository;

import java.util.List;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.user.entity.UmsPerson;

/**
 * 人员信息
 * @author feichongzheng
 *
 */
public interface UmsPersonRepository extends BaseRepository<UmsPerson,String> {
	
	List<UmsPerson> findBySn(String sn);
	
	List<UmsPerson> findByName(String name);
}
