package com.oauth.service;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import com.airag.ae.domain.RResource;
//import com.airag.ae.service.RResourceService;

public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

//	@Autowired
//	private RResourceService rresourceService;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		int firstQuestionMarkIndex = url.indexOf("?");
		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}
		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}
		System.out.println("url:" + url);
		List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
//		ConfigAttribute attribute = new SecurityConfig("ROLE_BASE");
//		result.add(attribute);
		try {
//			List<RResource> permList =rresourceService.findByResourceUrl(url);
//			if(permList.size()>0){
//			  for (RResource rResource : permList) {
//				  ConfigAttribute conf = new SecurityConfig(rResource.getRoleName());
//				  result.add(conf);
//			  }
//			}else{
				ConfigAttribute attribute = new SecurityConfig("BASE-SECURITY");
				result.add(attribute);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}