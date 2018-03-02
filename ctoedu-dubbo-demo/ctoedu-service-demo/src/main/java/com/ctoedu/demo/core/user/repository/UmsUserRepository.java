package com.ctoedu.demo.core.user.repository;

import java.util.List;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 上午10:31:08
 * Version:1.0
 */
public interface UmsUserRepository extends BaseRepository<UmsUser,String> {
	UmsUser findByUsername(String username);
	
	List<UmsUser> findByNickname(String nickname);
}
