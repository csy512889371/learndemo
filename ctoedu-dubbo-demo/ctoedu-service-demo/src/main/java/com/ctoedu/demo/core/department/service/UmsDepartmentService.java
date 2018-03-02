package com.ctoedu.demo.core.department.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.department.repository.UmsDepartmentRepository;
import com.ctoedu.demo.core.department.repository.UmsUserDepartmentRelationRepository;
import com.ctoedu.demo.core.position.service.UmsPositionService;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午3:34:32
 * Version:1.0
 */
@Service("umsDepartmentService")
public class UmsDepartmentService extends BaseService<UmsDepartment, String>{
	@Autowired
	private UmsUserDepartmentRelationRepository umsUserDepartmentRelationRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	@Autowired
	private UmsDepartmentRepository umsDepartmentRepository;
	@Autowired
	private UmsPositionService umsPositionService;
	/**
	 * 根据用户id查询用户所属组
	 * @param userId
	 * @param isAvailable
	 * @return
	 */
	public Set<String> findDepartmentIds(String userId,Short isAvailable) {
        Set<String> departmentIds = Sets.newHashSet();
        List<String> departmentIdList = umsUserDepartmentRelationRepository.findDepartmentIds(userId,isAvailable);
        if(departmentIdList!=null){
        	departmentIds.addAll(Sets.newHashSet(departmentIdList));
        }
        return departmentIds;
    }
	
    /**
     * 根据组id获取该组下的所有用户id
     * @param departmentId
     * @return
     */
    public List<String> findUserIdByDepartmentId(String departmentId){
    	return umsUserDepartmentRelationRepository.findUserIdByDepartmentId(departmentId);
    }
    
    public Page<UmsUser> findUserByDepartmentId(String departmentId, String nickname, String username, Pageable page){
    	return umsUserDepartmentRelationRepository.findUserByDepartmentId(departmentId, nickname, username, page);
    }
    
    public Page<UmsUser> findNotUserByDepartmentId(String departmentId, String nickname, String username, Pageable page){
    	return umsUserDepartmentRelationRepository.findNotUserByDepartmentId(departmentId, nickname, username, page);
    }
    
    public Page<UmsUser> findUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page){
    	return umsUserDepartmentRelationRepository.findUserByDepartmentIdAndNameAndSn(departmentId, name, sn, page);
    }
    
    public Page<UmsUser> findNotUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page){
    	return umsUserDepartmentRelationRepository.findNotUserByDepartmentIdAndNameAndSn(departmentId, name, sn, page);
    }
    
    /**
	 * 建立用户与组关系
	 * @param departmentId
	 * @param userIds
	 */
    public void buildUserDepartmentRelation(String departmentId, List<String> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	clearUserDepartmentRelation(departmentId, userIds);
        	for(String userId:userIds){
    			UmsUserDepartmentRelation r = new UmsUserDepartmentRelation();
    			
    			UmsDepartment ug = new UmsDepartment();
    			UmsUser user = new UmsUser();
    			ug.setId(departmentId);
                r.setDepartment(ug);
                user.setId(userId);
                r.setUser(user);
                umsUserDepartmentRelationRepository.save(r);
    		}
        }
    }
    
    /**
     * 删除用户与组关系
     * @param departmentId
     * @param userIds
     */
    public void clearUserDepartmentRelation(String departmentId, List<String> userIds){
    	if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	umsUserDepartmentRelationRepository.clearUserDepartmentRelation(departmentId, userIds);
        }
    }
    
    public void deleteUserDepartmentRelation(String departmentId,List<String> userIds){
    	for(String userId : userIds){
    		umsUserDepartmentRelationRepository.deleteUserDepartmentRelation(departmentId, userId);
    	}
    }
    
    /**
     * 删除组
     * @param departmentIds
     */
    public void deleteDepartment(String... departmentIds){
    	for(String departmentId:departmentIds){
    		umsAclRepository.clearPrincipalAcl(departmentId, Principal.PRINCIPAL_DEPARTMENT);
    		umsUserDepartmentRelationRepository.clearUserDepartmentRelation(departmentId);
    		List<UmsPosition> positions = umsPositionService.findByDepartmentId(departmentId);
    		for(UmsPosition position : positions){
    			umsPositionService.deletePosition(position.getId());
    		}
    		delete(departmentId);
    	}
    }
    
    public List<UmsDepartment> findByOrgId(String orgId){
    	return umsDepartmentRepository.findByOrgId(orgId);
    }
}
