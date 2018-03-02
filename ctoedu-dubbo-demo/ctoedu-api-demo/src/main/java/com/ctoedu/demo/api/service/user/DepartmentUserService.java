package com.ctoedu.demo.api.service.user;

import java.util.List;

import com.ctoedu.common.vo.PageVO;
import com.ctoedu.demo.api.controller.vo.person.PersonVO;
import com.ctoedu.demo.api.controller.vo.user.DepartmentUserVO;

public interface DepartmentUserService {
	
	PageVO<DepartmentUserVO> getUser(String nickname, String username, String departmentId, int number, int size, String currentUsername, List<String> currentUserRoleSn);

	PageVO<PersonVO> getPerson(String name, String sn, String departmentId, int number, int size, String currentUsername, List<String> currentUserRoleSn);
}
