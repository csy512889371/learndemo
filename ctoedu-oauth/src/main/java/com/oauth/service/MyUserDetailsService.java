package com.oauth.service;

import com.oauth.facade.OURegisterResponse;
import com.oauth.model.UCenterRemoteCall;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	private LdapPersonService ldapPersonService;
//	
//	@Autowired
//	private URoleService rRoleService;
	
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		org.springframework.security.core.userdetails.User userDetails = null;
		try {
			//List<Person> persons = ldapPersonService.getPerson(userName, "");
			OURegisterResponse request=UCenterRemoteCall.remoteCallAccount(userName);
			if(request.getLogin_name() == null) {
				throw new Exception("帐号："+userName+" 不存在！");  
			}
           System.out.println(request);
			Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(request.getLogin_name());  
			//System.out.println(person.getCn()+" "+person.getUserPassword());
			boolean enables = true;  
	        boolean accountNonExpired = true;  
	        boolean credentialsNonExpired = true;  
	        boolean accountNonLocked = true; 
	        userDetails = new org.springframework.security.core.userdetails.User(request.getLogin_name(),request.getUser_password(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);  
		}catch(Exception e) {
			//log.error(e.getMessage());
			e.printStackTrace();
		}
		return userDetails;
	}
	/**
	 * 根据用户获取该用户拥有的角色
	 * @param user
	 * @return
	 */
	private Set<GrantedAuthority> getGrantedAuthorities(String userid) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();  
//		URole uRole=rRoleService.getURole(userid);
//        if(uRole != null) {
//        	for(String role : uRole.getRoleNames()) {  
//        		grantedAuthorities.add(new SimpleGrantedAuthority(role));
//            }  
//        }
        return grantedAuthorities;  
	}
}
