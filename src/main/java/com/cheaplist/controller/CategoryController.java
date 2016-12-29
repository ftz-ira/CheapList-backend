package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cheaplist.exception.CategoryNotFound;
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
