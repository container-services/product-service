package com.containercrush.product.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.containercrush.product.service.CloudObjectService;
import com.ibm.cloud.objectstorage.util.IOUtils;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/products")
public class ProductsCatalogController {
	@Autowired
	private ServletContext servletContext;
	 
	@Autowired
	CloudObjectService objectService;
	
	@RequestMapping(value = "/v1/list/products", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<?> listAllProducts(){
		return null;
	}
	
	@RequestMapping(value = "/Image/{id:.+}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws Exception{
		ResponseEntity<byte[]> responseEntity;
		final HttpHeaders headers = new HttpHeaders();
	    InputStream imageStream = objectService.getObject("1004.jpg");
	    byte [] image = IOUtils.toByteArray(imageStream);
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        responseEntity = new ResponseEntity<>(image, headers, HttpStatus.OK);
        return responseEntity;
	    
	}
	
	@RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException{
	    HttpHeaders headers = new HttpHeaders();
	    InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/ArchDiagram.png");
	    byte[] media = IOUtils.toByteArray(in);
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	    
	    headers.setContentType(MediaType.IMAGE_PNG);
	    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
	    
	    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	    
	    //return ResponseEntity.ok()
	            //.contentLength(in.)
	            //.contentType(MediaType.IMAGE_PNG)
	            //.body(new InputStreamResource(in));
	    
	    
	}
	
	@RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
	public void getImageAsByteArray(HttpServletResponse response, @RequestParam(name = "id") String imageId) throws IOException {
		System.out.println("-----Called  getImageAsByteArray------------- ID = "+imageId);
	    //InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/ArchDiagram.png");
		InputStream imageStream = objectService.getObject(imageId+".jpg");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(imageStream, response.getOutputStream());
	}
}
