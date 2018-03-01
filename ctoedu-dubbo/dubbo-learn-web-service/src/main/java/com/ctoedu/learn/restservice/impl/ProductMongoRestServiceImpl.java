package com.ctoedu.learn.restservice.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ctoedu.learn.jpa.domain.Product;
import com.ctoedu.learn.repo.IProductMongoService;
import com.ctoedu.learn.restservice.IProductMongoRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;


@Path("/mongo/product")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Service("productMongoRestService")
public class ProductMongoRestServiceImpl implements IProductMongoRestService {

	@Autowired
	IProductMongoService productMongoService;
	
	@Path("/getproduct/{productId}")
	@GET
	public Product getProductById(@PathParam("productId") int productId) {
		// TODO Auto-generated method stub
		return productMongoService.getProductById(productId);
	}
    
	@Path("/insertproduct")
	@POST
	public void insertProduct(@RequestBody Product product) {
		productMongoService.insertProduct(product);
	}

	@Path("/deleteproduct/{productId}")
	@DELETE
	public void deleteProduct(@PathParam("productId") int productId) {
		productMongoService.deleteProduct(productId);
	}

}
