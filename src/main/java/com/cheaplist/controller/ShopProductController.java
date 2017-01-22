package com.cheaplist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ErrorResponse;
import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.cheaplist.service.ProductService;
import com.cheaplist.service.ShopProductService;
import com.cheaplist.service.ShopService;
import com.cheaplist.validator.ShopProductValidator;

/**
 * CREATE --> POST /prices READ --> DONE PATCH --> DELETE --> DENY
 * 
 */

// Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/prices")
public class ShopProductController {

	@Autowired
	private ShopProductService shopProductService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ShopProductValidator shopProductValidator;

	@Autowired
	private ShopService shopService;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(shopProductValidator);
	}

	/***** PRICE BY PRODUCT ALL SHOP ******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{idProduct}", method = RequestMethod.GET)
	public ResponseEntity<List<ShopProduct>> PriceProduct(@PathVariable Integer idProduct) {
		return new ResponseEntity<List<ShopProduct>>(shopProductService.findPriceByProduct(idProduct.intValue()),
				HttpStatus.OK);
	}

	/**** PRICE BY PRODUCT BY SHOP *******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{idProduct}/shop/{idShop}", method = RequestMethod.GET)
	public ResponseEntity<ShopProduct> PriceProductShop(@PathVariable Integer idProduct, @PathVariable Integer idShop) {
		return new ResponseEntity<ShopProduct>(
				shopProductService.findPriceByProductShop(idProduct.intValue(), idShop.intValue()), HttpStatus.OK);
	}

	/****
	 * CREATE PRICE BY PRODUCT AND BY SHOP
	 * 
	 * @throws ExceptionMessage
	 *****/
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ShopProduct> createNewPrice(@RequestBody ShopProduct shopProduct, BindingResult result)
			throws ExceptionMessage {
		shopProductValidator.validate(shopProduct, result);
		// System.out.println(result.getAllErrors());
		if (result.hasErrors())
			throw new ExceptionMessage("ERROR CREATE PRICE");
		shopProduct = shopProductService.create(shopProduct);
		return new ResponseEntity<ShopProduct>(shopProduct, HttpStatus.OK);
	}

	/****
	 * PATCH PRICE BY PRODUCT AND BY SHOP
	 ******/
	@JsonView(View.PriceProduct.class)
	@RequestMapping(value = "/{idProduct}/shop/{idShop}", method = RequestMethod.PATCH)
	public ResponseEntity<ShopProduct> PatchPrices(@PathVariable Integer idProduct, @PathVariable Integer idShop,
			@RequestBody ShopProduct shopProductJson, BindingResult result) throws ExceptionMessage {

		shopProductValidator.validate(shopProductJson, result);
		if (result.hasErrors())
			throw new ExceptionMessage("ERROR PATCH PRICE");

		ShopProduct shopProduct = shopProductService.findPriceByProductShop(idProduct, idShop);
		ShopProduct newshopProduct = new ShopProduct();

		/*** Il n'a pas de lignes pour ce prix ****/
		if (shopProduct == null) {
			newshopProduct.setProduct(productService.findById(idProduct));
			if (newshopProduct.getProduct() == null)
				throw new ExceptionMessage("ERROR PRODUCT ID INVALID");

			newshopProduct.setShop(shopService.findById(idShop));
			if (newshopProduct.getProduct() == null)
				throw new ExceptionMessage("ERROR SHOP ID INVALID");

			newshopProduct.setPrice(shopProductJson.getPrice());
			newshopProduct.setRatio(0);

			 newshopProduct = shopProductService.create(newshopProduct);
			 if (newshopProduct == null) throw new ExceptionMessage("ERROR CREATE SHOPLIST PRICE");
		} else {
			shopProductJson.setId(shopProduct.getId());
			shopProductJson.setProduct(productService.findById(idProduct));
			shopProductJson.setShop(shopService.findById(idShop));
			newshopProduct = shopProductService.update(shopProductJson);
			if (newshopProduct == null)
				throw new ExceptionMessage("ERROR UPDATE SHOPLIST PRICE");
		}
		return new ResponseEntity<ShopProduct>(newshopProduct, HttpStatus.OK);
	}

	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}

}
