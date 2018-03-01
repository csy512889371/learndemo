package com.ctoedu.learn.restservice.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ctoedu.learn.jpa.domain.Enterprise;
import com.ctoedu.learn.repo.IEnterpriseMongoService;
import com.ctoedu.learn.restservice.IEnterpriseMongoRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/mongo")
//@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_FORM_URLENCODED })
//@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Service("enterpriseMongoRestService")
public class EnterpriseMongoRestServiceImpl implements IEnterpriseMongoRestService {

    @Autowired
    IEnterpriseMongoService enterpriseMongoService;

    @Path("/getenterprise/{id}")
    @GET
    public Enterprise getEnterpriseById(@PathParam("id") int id) {
        return enterpriseMongoService.getEnterpriseById(id);
    }

    @Path("/insertenterprise")
    @POST
    public void insertEnterprise(@RequestBody Enterprise enterprise) {
        enterpriseMongoService.insertEnterprise(enterprise);
    }

    @Path("/getstring/{name}")
    @GET
    public String getString(@PathParam("name") String name) {
        return name;
    }

    @Path("/deleteenterprise/{id}")
    @DELETE
    public void deleteEnterprise(@PathParam("id") int enterpriseId) {
        enterpriseMongoService.deleteEnterprise(enterpriseId);
    }

}
