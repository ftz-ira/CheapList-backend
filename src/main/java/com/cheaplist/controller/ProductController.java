package com.cheaplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Product;
import com.cheaplist.model.View;
import com.cheaplist.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	/**** READ ONE PRODUCT  *****/
	@JsonView(View.Product.class)
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Product productOne(@PathVariable Integer id) {
	 Product product = productService.findById(id.intValue());
		return product;
	}


}
