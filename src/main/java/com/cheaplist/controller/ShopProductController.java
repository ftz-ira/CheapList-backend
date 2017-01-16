package com.cheaplist.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ShopProductNotFound;
import com.cheaplist.model.Member;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.cheaplist.service.ShopProductService;
import com.cheaplist.validator.MemberValidator;
import com.cheaplist.validator.ShopProductValidator;

/**
 * CREATE  --> POST  /prices
 * READ    --> DONE
 * PATCH   --> 
 * DELETE  --> DENY
 * 
 */	


//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/prices")
public class ShopProductController {

	@Autowired
	private ShopProductService shopProductService;
	
	@Autowired
	private ShopProductValidator shopProductValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(shopProductValidator);
	}
	

	/*****  PRICE BY PRODUCT ALL SHOP ******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{idProduct}",method = RequestMethod.GET)
	public List<ShopProduct> PriceProduct(@PathVariable Integer idProduct) {
		return shopProductService.findPriceByProduct(idProduct.intValue());
	}
	
	/**** PRICE BY PRODUCT BY SHOP *******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{idProduct}/shop/{idShop}" ,method = RequestMethod.GET)
	public ShopProduct PriceProductShop(@PathVariable Integer idProduct,@PathVariable Integer idShop) {
		return shopProductService.findPriceByProductShop(idProduct.intValue(), idShop.intValue());
	}
	
	/**** CREATE PRICE BY PRODUCT AND BY SHOP *****/
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<ObjectError> createNewPrice(@RequestBody ShopProduct shopProduct, BindingResult result) {
		shopProductValidator.validate(shopProduct, result);
		System.out.println(result.getAllErrors());
		if (result.hasErrors())
			return result.getAllErrors();	
		
		/*** Si tout est okay *****/
		shopProduct = shopProductService.create(shopProduct);		
		return result.getAllErrors();
	}
	
	/**** PATCH PRICE BY PRODUCT AND BY SHOP 
	 ******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/" ,method = RequestMethod.PATCH, consumes = "application/json")
	public List<ObjectError> UpdatePriceProductShop(@RequestBody ShopProduct shopProduct, BindingResult result) throws ShopProductNotFound {
		shopProductValidator.validate(shopProduct, result);
		if (result.hasErrors())
			return result.getAllErrors();	
		shopProduct = shopProductService.patch(shopProduct);
		shopProductService.update(shopProduct);		
		return result.getAllErrors();
	}
	

}
