package com.ctoedu.demo.core.org.service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ctoedu.common.entity.enums.BooleanEnum;
import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.department.service.UmsDepartmentService;
import com.ctoedu.demo.core.org.repository.UmsOrgRepository;
import com.ctoedu.demo.core.org.repository.UmsOrgRuleRepository;
import com.ctoedu.demo.core.org.repository.UmsOrgTypeRuleRepository;
import com.ctoedu.demo.core.org.repository.UmsUserOrgRelationRepository;
import com.ctoedu.demo.core.orgRole.service.UmsOrgRoleService;
import com.ctoedu.demo.core.position.service.UmsPositionService;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsOrgRule;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.org.exception.OrgChildNumOverException;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 * 
 * service接口
 * Date:2016-11-09 21:33
 * User:chenxiang
 * Version:1.0
 *
 */
@Service("umsOrgService")
public class UmsOrgService extends BaseService<UmsOrg,String>{
	
	@Autowired
	private UmsOrgRepository umsOrgRepository;
	@Autowired
	private UmsOrgRuleRepository umsOrgRuleRepository;
	@Autowired
	private UmsOrgTypeRuleRepository umsOrgTypeRuleRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	@Autowired
	private UmsUserOrgRelationRepository umsUserOrgRelationRepository;
	@Autowired
	private UmsDepartmentService umsDepartmentService;
	@Autowired
	private UmsPositionService umsPositionService;
	@Autowired
	private UmsOrgRoleService umsOrgRoleService;
	/**
	 * 过滤不可用的机构
	 * @param orgIds
	 * @return
	 */
	public Set<String> filterForCanAvailable(Set<String> orgIds) {
        Iterator<String> iter = orgIds.iterator();
        while (iter.hasNext()) {
            String id = iter.next();
            UmsOrg org = findOne(id);
            if (org == null || BooleanEnum.FALSE.getValue().equals(org.getIsAvailable())) {
            	orgIds.remove(id);
            }
        }
        return orgIds;
    }
	
	/**
	 * 检查机构下挂的节点数是否超过最大限制
	 * @param cOrg
	 * @param pOrg
	 */
	private void checkChildOrgNum(UmsOrg cOrg,UmsOrg pOrg) {
		if(pOrg==null) {
			return;
		}
		int rnum = umsOrgTypeRuleRepository.loadOrgTypeRuleNum(pOrg.getTypeId(), cOrg.getTypeId());
		if(rnum<0) {
			return ;
		}
		int hnum = umsOrgRepository.getOrgNumsByType(pOrg.getId(), cOrg.getTypeId());
		if(hnum>=rnum){
			throw new OrgChildNumOverException(new String[]{pOrg.getName(),cOrg.getName()});
		}
	}
	
	/**
	 * 添加组织机构
	 */
	public void saveUmsOrg(UmsOrg org) {
		checkChildOrgNum(org, org.getParent());
		if(org.getParent()==null) {
			org.setOrderNum(1);
		} else {
			org.setOrderNum(umsOrgRepository.getMaxOrder(org.getParent().getId())+1);
		}
		save(org);
	}
	
	/**
	 * 添加一组子组织机构
	 * @param orgId
	 * @param cids
	 * @return 
	 */
	public UmsOrgRule saveUmsOrgRule(String orgId,String managerOrg){
		UmsOrgRule oru = umsOrgRuleRepository.loadManagerOrg(orgId);
		if(oru==null) {
			oru = new UmsOrgRule();
			oru.setOrgId(orgId);
			oru.setManagerOrg(managerOrg);
			return umsOrgRuleRepository.save(oru);
		} else {
			oru.setManagerOrg(managerOrg);
			return umsOrgRuleRepository.save(oru);
		}
	}
	
    /**
     * 根据主键Id删除组织机构
     * @param orgIds
     */
    public void deleteOrg(String... orgIds){
    	for(String orgId:orgIds){
    		umsAclRepository.clearPrincipalAcl(orgId, Principal.PRINCIPAL_ORG);
    		umsUserOrgRelationRepository.clearUserOrgRelation(orgId);
    		List<UmsOrg> orgs = umsOrgRepository.findByParentId(orgId);
    		for(UmsOrg org : orgs){
    			deleteOrg(org.getId());
    		}
    		List<UmsDepartment> departments = umsDepartmentService.findByOrgId(orgId);
    		for(UmsDepartment department:departments){
    			umsDepartmentService.deleteDepartment(department.getId());
    		}
    		List<UmsPosition> positions = umsPositionService.findByOrgId(orgId);
    		for(UmsPosition position : positions){
    			umsPositionService.deletePosition(position.getId());
    		}
    		List<UmsOrgRole> roles = umsOrgRoleService.findByOrgId(orgId);
    		for(UmsOrgRole role : roles){
    			umsOrgRoleService.deleteRole(role.getId());
    		}
    		delete(orgId);
    	}
    }
    
    /**
     * 根据主键Id删除组织机构规则
     * @param orgRuleIds
     */
    public void deleteOrgRule(String... orgRuleIds){
    	umsOrgRuleRepository.delete(orgRuleIds);
    }
    
    /**
     * 根据主键Id删除组织机构规则
     * @param orgRuleIds
     */
    public void deleteOrgRuleByOrg(String orgId){
    	umsOrgRuleRepository.deleteOrgRuleByOrg(orgId);
    }
    
    /**
     * 根据组织机构Id获取所管理的组织机构Id集合
     * @param orgId
     * @return
     */
    public List<String> listManagerRuleIds(String orgId){
    	List<String> idList = new ArrayList<String>();
    	String managerOrg = umsOrgRuleRepository.loadManagerOrg(orgId).getManagerOrg();
    	if(StringUtils.isNotEmpty(managerOrg)){
    		for(String id:managerOrg.split(",")){
    			idList.add(id);
    		}
    	}
    	return idList;
    }
    
    public Page<UmsUser> findUserByOrgId(String orgId, String nickname, String username, Pageable page){
    	return umsUserOrgRelationRepository.findUserByOrgId(orgId, nickname, username, page);
    }
    
    public Page<UmsUser> findNotUserByOrgId(String orgId, String nickname, String username, Pageable page){
    	return umsUserOrgRelationRepository.findNotUserByOrgId(orgId, nickname, username, page);
    }
    
    public Page<UmsUser> findUserByOrgIdAndNameAndSn(String orgId, String name, String sn, Pageable page){
    	return umsUserOrgRelationRepository.findUserByOrgIdAndNameAndSn(orgId, name, sn, page);
    }
    
    public Page<UmsUser> findNotUserByOrgIdAndNameAndSn(String orgId, String name, String sn, Pageable page){
    	return umsUserOrgRelationRepository.findNotUserByOrgIdAndNameAndSn(orgId, name, sn, page);
    }
    
    /**
	 * 建立用户与机构关系
	 * @param orgId
	 * @param userIds
	 */
    public void buildUserOrgRelation(String orgId, List<String> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	clearUserOrgRelation(orgId, userIds);
        	for(String userId:userIds){
    			UmsUserOrgRelation r = new UmsUserOrgRelation();
    			UmsOrg uo = new UmsOrg();
    			UmsUser user = new UmsUser();
    			uo.setId(orgId);
                r.setOrg(uo);
                user.setId(userId);
                r.setUser(user);
                umsUserOrgRelationRepository.save(r);
    		}
        }
    }
    
    /**
     * 删除用户与机构关系
     * @param departmentId
     * @param userIds
     */
    public void clearUserOrgRelation(String orgId, List<String> userIds){
    	if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	umsUserOrgRelationRepository.clearUserOrgRelation(orgId, userIds);
        }
    }
}