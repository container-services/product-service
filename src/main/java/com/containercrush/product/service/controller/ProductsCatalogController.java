package com.containercrush.product.service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products")
public class ProductsCatalogController {
	
	@RequestMapping(value = "/v1/list/products", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<?> listAllProducts(){
		return null;
	}
}
