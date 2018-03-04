package com.ldap.service.impl;

import javax.annotation.Resource;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import com.ldap.model.rest.OURequest;
import com.ldap.model.util.StrToJson;
import com.ldap.service.OUService;

@Service("oUService")
@Slf4j
public class OUServiceImpl implements OUService {

    @Resource(name = "ldapTemplate")
    private LdapTemplate ldapTemplate;

    public String addOU(OURequest oURequest) {
        try {
            BasicAttribute ba = new BasicAttribute("objectClass");
            ba.add("top");
            ba.add("organizationalUnit");
            Attributes attrs = new BasicAttributes();
            attrs.put(ba);
            if (oURequest.getRdn() != null && oURequest.getRdn().length() > 0) {
                if (oURequest.getOUName() != null && oURequest.getOUName().length() > 0) {
                    attrs.put("ou", oURequest.getOUName());
                } else {
                    return StrToJson.convert(1, "OUName can not null");
                }
            } else {
                return StrToJson.convert(1, "rdn can not null");
            }
            ldapTemplate.bind("ou=" + oURequest.getOUName() + "," + oURequest.getRdn(), null, attrs);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    public String delOU(String rdn) {
        try {
            if (rdn != null && rdn.length() > 0) {
                ldapTemplate.unbind(rdn);
            } else {
                return StrToJson.convert(1, "OrganizationName can not null");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

}
