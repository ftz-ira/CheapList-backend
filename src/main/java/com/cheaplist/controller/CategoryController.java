package com.cheaplist.controller;

import java.util.List;

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
import com.cheaplist.model.Category;
import com.cheaplist.model.View;
import com.cheaplist.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;

//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/*** READ ONE CATEGORY BY ID ****/
	@JsonView(View.Category.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> categoryOne(@PathVariable Integer id) throws ExceptionMessage {
		Category category = categoryService.findById(id.intValue());
		if (category == null) {
			throw new ExceptionMessage("CATEGORY ID NOT FOUND");
		}
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	/*** READ ALL CATEGORY *****/
	@JsonView(View.Category.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List <Category>> categoryAll() throws ExceptionMessage {
		List<Category> category = categoryService.findAll();
		return new ResponseEntity<List<Category>>(category, HttpStatus.OK);
	}
	
	
	/*** READ ALL PRODUCT BY CATEGORY ****/
	@JsonView(View.CategoryProduct.class)
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<Category> categoryAllProduct(@PathVariable Integer id) throws ExceptionMessage {
		Category category = categoryService.findById(id.intValue());
		if (category == null) {
			throw new ExceptionMessage("CATEGORY ALL PRODUCT ID  NOT FOUND");
		}
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}

}
