package com.ctoedu.demo.facade.position.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.department.service.UmsDepartmentService;
import com.ctoedu.demo.core.org.service.UmsOrgService;
import com.ctoedu.demo.core.position.service.UmsPositionService;
import com.ctoedu.demo.core.position.service.UmsUserPositionRelationService;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.exception.PositionException;
import com.ctoedu.demo.facade.position.exception.PositionSnExistsException;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Service(retries=2)
public class UmsPositionFacadeImpl implements UmsPositionFacade {
	
	@Autowired
	private UmsPositionService umsPositionService;
	
	@Autowired
	private UmsOrgService umsOrgService;
	
	@Autowired
	private UmsDepartmentService umsDepartmentService;
	
	@Autowired
	private UmsUserPositionRelationService umsUserPositionRelationService;

	@Override
	public UmsPosition create(UmsPosition umsPosition) throws PositionSnExistsException {
		String orgId = umsPosition.getOrgId();
		String departmentId = umsPosition.getDepartmentId();
		if(orgId != null) {
			UmsOrg org = umsOrgService.findOne(orgId);
			if(org == null){
				throw new PositionException("position.org.not.exited", null);
			}
		}
		if(departmentId != null) {
			UmsDepartment department = umsDepartmentService.findOne(departmentId);
			if (department == null) {
				throw new PositionException("position.department.not.exited", null);
			}
			if (orgId == null) {
				umsPosition.setOrgId(department.getOrgId());
			}
		}
		return umsPositionService.savePosition(umsPosition);
	}

	@Override
	public UmsPosition update(UmsPosition umsPosition) {
		return umsPositionService.update(umsPosition);
	}

	@Override
	public void delete(String... ids) {
		umsPositionService.deletePosition(ids);
	}

	@Override
	public UmsPosition getBySn(String sn) {
		return umsPositionService.findBySn(sn);
	}

	@Override
	public UmsPosition getById(String id) {
		return umsPositionService.findOne(id);
	}

	@Override
	public Page<UmsPosition> listPage(Searchable searchable) {
		return umsPositionService.findAll(searchable);
	}

	@Override
	public List<UmsPosition> list(Searchable searchable) {
		return umsPositionService.findAllWithNoPageNoSort(searchable);
	}

	public UmsPosition updAvailable(String id, Short isAvailable) {
		UmsPosition position = umsPositionService.findOne(id);
		position.setIsAvailable(isAvailable);
		position = umsPositionService.update(position);
		return position;
	}
	
	@Override
	public Page<UmsUser> findUserByPositionId(String positionId, String nickname, String username, Pageable page) {
		return umsPositionService.findUserByPositionId(positionId, nickname, username, page);
	}

	@Override
	public Page<UmsUser> findNotUserByPositionId(String positionId, String nickname, String username, Pageable page) {
		return umsPositionService.findNotUserByPositionId(positionId, nickname, username, page);
	}
	
	@Override
	public void buildUserPositionRelation(String positionId, List<String> userIds) {
		umsPositionService.buildUserPositionRelation(positionId, userIds);
	}
	
	@Override
	public void clearUserPositionRelation(String positionId, List<String> userIds){
		umsPositionService.clearUserPositionRelation(positionId, userIds);
	}

	@Override
	public Page<UmsUserPositionRelation> listUmsUserPositionRelationPage(Searchable searchable) {
		return umsUserPositionRelationService.findAll(searchable);
	}

	@Override
	public List<UmsUserPositionRelation> listUmsUserPositionRelation(Searchable searchable) {
		return umsUserPositionRelationService.findAllWithSort(searchable);
	}

	@Override
	public Page<UmsUser> findUserByPositionIdAndNameAndSn(String positionId, String name, String sn, Pageable page) {
		return umsPositionService.findUserByPositionIdAndNameAndSn(positionId, name, sn, page);
	}

	@Override
	public Page<UmsUser> findNotUserByPositionIdAndNameAndSn(String positionId, String name, String sn, Pageable page) {
		return umsPositionService.findNotUserByPositionIdAndNameAndSn(positionId, name, sn, page);
	}
}
