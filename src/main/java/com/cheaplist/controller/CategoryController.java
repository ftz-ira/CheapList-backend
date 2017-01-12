package com.cheaplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Category;
import com.cheaplist.model.View;
import com.cheaplist.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/*** READ ONE CATEGORY BY ID ****/
	@JsonView(View.Category.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Category categoryOne(@PathVariable Integer id) {
		return categoryService.findById(id.intValue());
	}

	/*** READ ALL PRODUCT BY CATEGORY ****/
	@JsonView(View.CategoryProduct.class)
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
	public Category categoryAllProduct(@PathVariable Integer id) {
		return categoryService.findById(id.intValue());
	}

}
