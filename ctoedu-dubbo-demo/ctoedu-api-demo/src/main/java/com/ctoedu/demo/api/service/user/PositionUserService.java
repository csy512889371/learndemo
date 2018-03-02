package com.ctoedu.demo.api.service.user;

import java.util.List;

import com.ctoedu.common.vo.PageVO;
import com.ctoedu.demo.api.controller.vo.person.PersonVO;
import com.ctoedu.demo.api.controller.vo.user.PositionUserVO;

public interface PositionUserService {

	PageVO<PositionUserVO> getUser(String nickname, String username, String positionId, int number, int size, String currentUsername, List<String> currentUserRoleSn);

	PageVO<PersonVO> getPerson(String name, String sn, String positionId, int number, int size, String currentUsername, List<String> currentUserRoleSn);
}
