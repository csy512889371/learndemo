package com.ctoedu.demo.facade.orgRole.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.orgRole.service.UmsOrgRoleService;
import com.ctoedu.demo.core.orgRole.service.UmsUserOrgRoleRelationService;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.exception.OrgRoleSnExistsException;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Service(retries=2)
public class UmsOrgRoleFacadeImpl implements UmsOrgRoleFacade {
	
	@Autowired
	private UmsOrgRoleService umsOrgRoleService;
	@Autowired
	private UmsUserOrgRoleRelationService umsUserOrgRoleRelationService;

	@Override
	public UmsOrgRole create(UmsOrgRole umsOrgRole) throws OrgRoleSnExistsException {
		return umsOrgRoleService.saveRole(umsOrgRole);
	}

	@Override
	public UmsOrgRole update(UmsOrgRole umsOrgRole) {
		return umsOrgRoleService.update(umsOrgRole);
	}

	@Override
	public void delete(String... ids) {
		umsOrgRoleService.deleteRole(ids);
	}

	@Override
	public UmsOrgRole getBySn(String sn) {
		return umsOrgRoleService.findByRoleSn(sn);
	}

	@Override
	public UmsOrgRole getById(String id) {
		return umsOrgRoleService.findOne(id);
	}

	@Override
	public Page<UmsOrgRole> listPage(Searchable searchable) {
		return umsOrgRoleService.findAll(searchable);
	}

	@Override
	public List<UmsOrgRole> list(Searchable searchable) {
		return umsOrgRoleService.findAllWithNoPageNoSort(searchable);
	}

	public UmsOrgRole updAvailable(String id, Short isAvailable) {
		UmsOrgRole role = umsOrgRoleService.findOne(id);
		role.setIsAvailable(isAvailable);
		role = umsOrgRoleService.update(role);
		return role;
	}

	@Override
	public void clearUserRoleRelation(String roleId, List<String> userIds) {
		umsOrgRoleService.clearUserRoleRelation(roleId, userIds);
	}

	@Override
	public Page<UmsUser> findUserByRoleId(String roleId, String nickname, String username, Pageable page) {
		return umsOrgRoleService.findUserByRoleId(roleId, nickname, username, page);
	}

	@Override
	public Page<UmsUser> findNotUserByRoleId(String roleId, String nickname, String username, Pageable page) {
		return umsOrgRoleService.findNotUserByRoleId(roleId, nickname, username, page);
	}

	@Override
	public void buildUserRoleRelation(String roleId, String[] userIds) {
		umsOrgRoleService.buildUserRoleRelation(roleId, userIds);
	}

	@Override
	public Page<UmsUserOrgRoleRelation> listUmsUserRoleRelationPage(Searchable searchable) {
		return umsUserOrgRoleRelationService.findAll(searchable);
	}

	@Override
	public List<UmsUserOrgRoleRelation> listUmsUserRoleRelation(Searchable searchable) {
		return umsUserOrgRoleRelationService.findAllWithSort(searchable);
	}

	@Override
	public void deleteByOrg(String[] orgIds) {
		umsOrgRoleService.deleteByOrgIdIn(orgIds);
	}

	@Override
	public Page<UmsUser> findUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page) {
		return umsOrgRoleService.findUserByRoleIdAndNameAndSn(roleId, name, sn, page);
	}

	@Override
	public Page<UmsUser> findNotUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page) {
		return umsOrgRoleService.findNotUserByRoleIdAndNameAndSn(roleId, name, sn, page);
	}
}
