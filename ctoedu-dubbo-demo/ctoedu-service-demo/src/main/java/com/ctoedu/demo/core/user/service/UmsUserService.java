package com.ctoedu.demo.core.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.entity.enums.BooleanEnum;
import com.ctoedu.common.service.BaseService;
import com.ctoedu.common.utils.security.Md5Utils;
import com.ctoedu.demo.core.user.repository.UmsPersonRepository;
import com.ctoedu.demo.core.user.repository.UmsUserRepository;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.exception.UserException;
import com.ctoedu.demo.facade.user.exception.UserNicknameEmptyException;
import com.ctoedu.demo.facade.user.exception.UserNotExistsException;
import com.ctoedu.demo.facade.user.exception.UserPasswordEmptyException;
import com.ctoedu.demo.facade.user.exception.UserPasswordNotMatchException;
import com.ctoedu.demo.facade.user.exception.UserUnAvailableException;
import com.ctoedu.demo.facade.user.exception.UserUsernameEmptyException;
import com.ctoedu.demo.facade.user.exception.UserUsernameExistsException;

/**
 *
 * Date:2016年11月23日 下午3:39:47
 * Version:1.0
 */
@Service("umsUserService")
public class UmsUserService extends BaseService<UmsUser, String>{
	@Autowired
	private UmsUserRepository umsUserRepository;
	
	@Autowired
	private UmsPersonRepository umsPersonRepository;
	
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
    public UmsUser findByUsername(String username){
        return umsUserRepository.findByUsername(username);
    }
    
    /**
	 * 根据昵称获取用户
	 * @param username
	 * @return
	 */
    public List<UmsUser> findByNickname(String nickname){
        return umsUserRepository.findByNickname(nickname);
    }
    
    /**
     * 修改密码
     * @param user
     * @param newPassword
     * @return
     */
    public UmsUser changePassword(UmsUser user, String newPassword) {
        user.generateSalt();
        user.setPassword(encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        user = update(user);
        return user;
    }
    
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public UmsUser login(String username, String password) {
    	
    	if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UserNotExistsException();
        }

        UmsUser user = findByUsername(username);
        boolean flag = true;
        if (user == null || BooleanEnum.TRUE.getValue().equals(user.getDeleted())) {
        	flag = false;
        }else if (AvailableEnum.FALSE.getValue().equals(user.getIsAvailable())) {
        	flag = false;
        }else if (!matches(user, password)) {
        	flag = false;
        }
        
        if(!flag){
        	boolean userExistFlag = false;
        	boolean userAvailableFlag = false;
        	boolean userPasswordMatchFlag = false;
        	List<UmsUser> users = findByNickname(username);
        	List<UmsUser> usersWithNickNameAndPassword = new ArrayList<>();
        	if(users != null){
        		for(UmsUser u : users){
            		if(u != null && BooleanEnum.FALSE.getValue().equals(u.getDeleted())){
            			userExistFlag = true;
            			if(AvailableEnum.TRUE.getValue().equals(u.getIsAvailable())){
                			userAvailableFlag = true;
                			if(matches(u, password)){
                    			userPasswordMatchFlag = true;
                    			usersWithNickNameAndPassword.add(u);
                    		}
                		}
            		}
            	}
        	}
    		List<UmsPerson> personsBySn = umsPersonRepository.findBySn(username);
    		for(UmsPerson person : personsBySn){
    			UmsUser u = person.getUser();
    			if(u != null && BooleanEnum.FALSE.getValue().equals(u.getDeleted())){
    				userExistFlag = true;
        			if(AvailableEnum.TRUE.getValue().equals(u.getIsAvailable())){
            			userAvailableFlag = true;
            			if(matches(u, password)){
                			userPasswordMatchFlag = true;
                			usersWithNickNameAndPassword.add(u);
                		}
            		}
    			}
    		}
    		List<UmsPerson> personsByName = umsPersonRepository.findByName(username);
    		for(UmsPerson person : personsByName){
    			UmsUser u = person.getUser();
    			if(u != null && BooleanEnum.FALSE.getValue().equals(u.getDeleted())){
    				userExistFlag = true;
        			if(AvailableEnum.TRUE.getValue().equals(u.getIsAvailable())){
            			userAvailableFlag = true;
            			if(matches(u, password)){
                			userPasswordMatchFlag = true;
                			usersWithNickNameAndPassword.add(u);
                		}
            		}
    			}
    		}
        	if(usersWithNickNameAndPassword.size() > 1){
        		String uid = null;
        		for(UmsUser u : usersWithNickNameAndPassword){
        			if(uid == null){
        				uid = u.getId();
        			}else{
        				if(!uid.equals(u.getId())){
        					throw new UserException("user.password.not.safe", null);
        				}
        			}
        		}
        	}
        	if(!userExistFlag){
        		throw new UserNotExistsException();
        	}
        	if(!userAvailableFlag){
        		throw new UserUnAvailableException();
        	}
        	if(!userPasswordMatchFlag){
        		throw new UserPasswordNotMatchException();
        	}
        	user = usersWithNickNameAndPassword.get(0);
        }
        return user;
    }
    
    /**
     * 用户注册
     * @param user
     * @return
     */
    public UmsUser register(UmsUser user) {
    	String username = user.getUsername();
    	String password = user.getPassword();
    	String nickname = user.getNickname();
    	
    	UmsPerson person = user.getPerson();
    	
		if(person == null) {
			person = new UmsPerson();
			person.setUser(user);
			user.setPerson(person);
		} else {
			if (person.getName() != null && person.getName().length()>12) {
	            throw new UserException("user.person.name.too.long", null);
	        }
			List<UmsPerson> list = umsPersonRepository.findBySn(person.getSn());
			if (list != null && list.size() > 0) {
	            throw new UserException("user.person.sn.exists", null);
	        }
			person.setUser(user);
		}
    	
    	if (StringUtils.isEmpty(username)) {
            throw new UserUsernameEmptyException();
        }
    	if(StringUtils.isEmpty(password)){
    		throw new UserPasswordEmptyException();
    	}
    	if(StringUtils.isEmpty(nickname)){
    		throw new UserNicknameEmptyException();
    	}
//    	if (!ValidateUtils.validateUsername(username)) {
//            throw new UserUsernameNotValidException();
//        }
//    	if (!ValidateUtils.validatePassword(password)) {
//            throw new UserPasswordNotValidException();
//        }
    	if (nickname.length()>12) {
            throw new UserException("user.nickname.too.long", null);
        }

        if (findByUsername(username) != null) {
            throw new UserUsernameExistsException();
        }
        
		user.generateSalt();
		user.setPassword(encryptPassword(username, password, user.getSalt()));
		user.setCreateDate(new Date());
		
        return umsUserRepository.save(user);
    }
    
    private boolean matches(UmsUser user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getUsername(), newPassword, user.getSalt()));
    }
    
    private String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }
}
