package com.cheaplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ErrorResponse;
import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Product;
import com.cheaplist.model.View;
import com.cheaplist.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;


//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	/**** READ ONE PRODUCT  
	 * @throws ExceptionMessage *****/
	@JsonView(View.Product.class)
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Product> productOne(@PathVariable Integer id) throws ExceptionMessage {
	 Product product = productService.findById(id.intValue());
	 if (product == null) throw new ExceptionMessage("ID PRODUCT NOT FIND");
	 return new ResponseEntity<Product> (product, HttpStatus.OK);
	}

	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}

}
