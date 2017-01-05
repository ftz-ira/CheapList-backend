package com.cheaplist.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.model.ShopProduct;
import com.cheaplist.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.cheaplist.service.ShopProductService;

@RestController
@RequestMapping(value = "/prices")
public class ShopProductController {

	@Autowired
	private ShopProductService shopProductService;

	/*****  PRICE BY PRODUCT ALL SHOP ******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{id}")
	public List<ShopProduct> PriceProduct(@PathVariable Integer id) {
		return shopProductService.findPriceByProduct(id.intValue());
	}
	
	/**** PRICE BY PRODUCT BY SHOP *******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{idproduct}/shop/{idshop}")
	public List<ShopProduct> PriceProductShop(@PathVariable Integer idproduct,@PathVariable Integer idshop) {
		return shopProductService.findPriceByProductShop(idproduct.intValue(), idshop.intValue());
	}
		
	

}
