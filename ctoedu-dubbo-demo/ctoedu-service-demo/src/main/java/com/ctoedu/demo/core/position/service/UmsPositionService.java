package com.ctoedu.demo.core.position.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ctoedu.common.entity.enums.BooleanEnum;
import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.position.repository.UmsPositionRepository;
import com.ctoedu.demo.core.position.repository.UmsUserPositionRelationRepository;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.exception.PositionSnExistsException;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午3:36:00
 * Version:1.0
 */
@Service("umsPositionService")
public class UmsPositionService extends BaseService<UmsPosition, String>{
	@Autowired
	private UmsPositionRepository umsPositionRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	@Autowired
	private UmsUserPositionRelationRepository umsUserPositionRelationRepository;
	
	public Set<String> filterForCanAvailable(Set<String> posIds) {
        Iterator<String> iter1 = posIds.iterator();

        while (iter1.hasNext()) {
            String id = iter1.next();
            UmsPosition pos = findOne(id);
            if (pos == null || BooleanEnum.FALSE.getValue().equals(pos.getIsAvailable())) {
            	posIds.remove(id);
            }
        }
        return posIds;
    }
	
	/**
	 * 保存职位
	 * @param position
	 */
	public UmsPosition savePosition(UmsPosition position){
		if(findBySn(position.getSn())!=null){
            throw new PositionSnExistsException();
        }
        return save(position);
	}
	
	/**
	 * 根据职位标识符查找职位
	 * @param sn
	 * @return
	 */
	public UmsPosition findBySn(String sn){
		return umsPositionRepository.findBySn(sn);
	}
	
    /**
     * 删除职位
     * @param posIds
     */
    public void deletePosition(String... posIds){
    	for(String posId:posIds){
    		umsAclRepository.clearPrincipalAcl(posId, Principal.PRINCIPAL_POSITION);
    		umsUserPositionRelationRepository.clearUserPositionRelation(posId);
    		delete(posId);
    	}
    }
    
    public Page<UmsUser> findUserByPositionId(String positionId, String nickname, String username, Pageable page){
    	return umsUserPositionRelationRepository.findUserByPositionId(positionId, nickname, username, page);
    }
    
    public Page<UmsUser> findNotUserByPositionId(String positionId, String nickname, String username, Pageable page){
    	return umsUserPositionRelationRepository.findNotUserByPositionId(positionId, nickname, username, page);
    }
    
    public Page<UmsUser> findUserByPositionIdAndNameAndSn(String positionId, String name, String sn, Pageable page){
    	return umsUserPositionRelationRepository.findUserByPositionIdAndNameAndSn(positionId, name, sn, page);
    }
    
    public Page<UmsUser> findNotUserByPositionIdAndNameAndSn(String positionId, String name, String sn, Pageable page){
    	return umsUserPositionRelationRepository.findNotUserByPositionIdAndNameAndSn(positionId, name, sn, page);
    }
    
    /**
	 * 建立用户与机构关系
	 * @param orgId
	 * @param userIds
	 */
    public void buildUserPositionRelation(String positionId, List<String> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	clearUserPositionRelation(positionId, userIds);
        	for(String userId:userIds){
    			UmsUserPositionRelation r = new UmsUserPositionRelation();
    			UmsPosition position = new UmsPosition();
    			position.setId(positionId);
    			UmsUser user = new UmsUser();
    			user.setId(userId);
    			r.setPosition(position);
    			r.setUser(user);
                umsUserPositionRelationRepository.save(r);
    		}
        }
    }
    
    /**
     * 删除用户与机构关系
     * @param departmentId
     * @param userIds
     */
    public void clearUserPositionRelation(String positionId, List<String> userIds){
    	if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	umsUserPositionRelationRepository.clearUserPositionRelation(positionId, userIds);
        }
    }
    
    public List<UmsPosition> findByDepartmentId(String departmentId){
    	return umsPositionRepository.findByDepartmentId(departmentId);
    }
    
    public List<UmsPosition> findByOrgId(String orgId){
    	return umsPositionRepository.findByOrgId(orgId);
    }
}
