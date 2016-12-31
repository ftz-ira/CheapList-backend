package com.cheaplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Category;
import com.cheaplist.model.View;
import com.cheaplist.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@JsonView(View.CategoryProduct.class)
	@RequestMapping(value="/get/{id}")
	public Category newBrandcategory(@PathVariable Integer id) {
		Category category;
		category = categoryService.findById(id.intValue());
		return category;
					}

	
}
