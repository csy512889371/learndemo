package com.ctoedu.demo.core.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.exception.UserUsernameExistsException;

/**
 *
 * Date:2016年11月23日 下午3:38:37
 * Version:1.0
 */
@Service("umsPersonService")
public class UmsPersonService extends BaseService<UmsPerson, String>{
	
	@Autowired
	private UmsUserService umsUserService;
	
    /**
     * 保存人员
     * @param person
     */
    public UmsPerson savePerson(UmsPerson person) {
        if(umsUserService.findByUsername(person.getUser().getUsername())!=null){
            throw new UserUsernameExistsException();
        }
        person = save(person);
//        UmsUserOrgGroupPos umsPersonOrgPos = new UmsUserOrgGroupPos();
//        umsPersonOrgPos.setUserId(person.getUser().getId());
//        umsPersonOrgPos.setOrgId(person.getOrgId());
//        umsPersonOrgPos.setPosId(person.getPosId());
//        umsUserOrgGroupPosRepository.save(umsPersonOrgPos);
        return person;
    }
    
    /**
     * 更新人员
     * @param person
     */
    public UmsPerson updatePerson(UmsPerson person){
    	person = update(person);
//    	if(person.getUser().getDeleted()==BooleanEnum.FALSE.getValue()){
//    		UmsUserOrgGroupPos umsPersonOrgPos = umsUserOrgGroupPosRepository.findUmsUserOrgGroupPosByPersonId(person.getUser().getId());
//            umsPersonOrgPos.setOrgId(person.getOrgId());
//            umsPersonOrgPos.setPosId(person.getPosId());
//            umsUserOrgGroupPosRepository.save(umsPersonOrgPos);
//    	}
    	return person;
    }
}
