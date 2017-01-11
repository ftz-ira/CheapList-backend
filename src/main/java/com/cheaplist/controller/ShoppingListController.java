package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.exception.ShoppingListNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.model.View;
import com.cheaplist.service.ShoppingListService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/lists")
public class ShoppingListController {
	
	@Autowired
	private ShoppingListService shoppingListService;

	/*
	@Autowired
	private ShopValidator shoppingListValidator;
	*/
	
	/*** PATCH ONE ATTRIBUT FROM LIST  ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	public ShoppingList PatchShoppingList(@PathVariable Integer idList,	@RequestBody ShoppingList shoppingList) throws ShoppingListNotFound {
		System.out.println("Test Sebs");
		shoppingList = shoppingListService.patch(idList,shoppingList);
		return shoppingList;

	}


	
}
