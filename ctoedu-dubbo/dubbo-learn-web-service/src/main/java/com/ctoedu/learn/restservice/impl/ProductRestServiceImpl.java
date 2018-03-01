package com.ctoedu.learn.restservice.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ctoedu.learn.mybatis.domain.Product;
import com.ctoedu.learn.repo.IProductService;
import com.ctoedu.learn.restservice.IProductRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

@Path("/product")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Service("productRestService")
public class ProductRestServiceImpl implements IProductRestService {

	@Autowired
	IProductService productService;
	
	@Path("/getproduct/{productId}")
	@GET
	public Product getProductById(@PathParam("productId") int productId) {
		// TODO Auto-generated method stub
		return productService.getProductById(productId);
	}
    
	@Path("/insertproduct")
	@POST
	public void insertProduct(@RequestBody Product product) {
		productService.insertProduct(product);
	}

	@Path("/deleteproduct/{productId}")
	@DELETE
	public void deleteProduct(@PathParam("productId") int productId) {
		productService.deleteProduct(productId);
	}

}
