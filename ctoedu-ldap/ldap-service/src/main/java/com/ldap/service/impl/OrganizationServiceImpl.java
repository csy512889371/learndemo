package com.ldap.service.impl;

import com.ldap.model.rest.OURequest;
import com.ldap.model.rest.OrganizationRequest;
import com.ldap.model.util.StrToJson;
import com.ldap.service.OUService;
import com.ldap.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

@Service("organizationService")
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    @Resource(name = "ldapTemplate")
    private LdapTemplate ldapTemplate;

    @Autowired
    private OUService oUservice;

    public String addOrganization(OrganizationRequest organizationRequest) {
        try {
            BasicAttribute ba = new BasicAttribute("objectClass");
            ba.add("top");
            ba.add("dcObject");
            ba.add("organization");
            Attributes attrs = new BasicAttributes();
            attrs.put(ba);
            if (organizationRequest.getOrganizationName() != null && organizationRequest.getOrganizationName().length() > 0) {
                attrs.put("o", organizationRequest.getOrganizationName());
            } else {
                return StrToJson.convert(1, "OrganizationName can not null");
            }
            if (organizationRequest.getDomainName() != null && organizationRequest.getDomainName().length() > 0) {
                attrs.put("dc", organizationRequest.getDomainName());
            } else {
                return StrToJson.convert(1, "DomainName can not null");
            }
            ldapTemplate.bind("o=" + organizationRequest.getOrganizationName() + "", null, attrs);
            createOU("o=" + organizationRequest.getOrganizationName() + "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    public String delOrganization(String rdn) {
        try {
            if (rdn != null && rdn.length() > 0) {
                ldapTemplate.unbind("o=" + rdn);
            } else {
                return StrToJson.convert(1, "OrganizationName can not null");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    /**
     * 创建公司的时候默认建立名称为personnelinformation和hierarchy的两个OU
     */
    public void createOU(String rdn) {
        try {
            OURequest oURequest = new OURequest();
            oURequest.setRdn(rdn);
            oURequest.setOUName("personnelinformation");
            oUservice.addOU(oURequest);
//		  OURequest oURequest1=new OURequest();
//		  oURequest1.setRdn(rdn);
//		  oURequest1.setOUName("hierarchy");
//		  oUservice.addOU(oURequest1);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
