package com.cheaplist.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ErrorResponse;
import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.model.View;
import com.cheaplist.service.ShoppingListService;
import com.fasterxml.jackson.annotation.JsonView;


//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/lists")
public class ShoppingListController {
	
	@Autowired
	private ShoppingListService shoppingListService;

	/*
	@Autowired
	private ShopValidator shoppingListValidator;
	*/
	
	/**
	 * CREATE  --> DENY
	 * READ    --> DEBUG MODE ONLY (HTTP MODE : PUT)
	 * PATCH   --> 
	 * DELETE  --> SAVE ET CREATE
	 * 
	 */	
	
	/*** PATCH ONE ATTRIBUT FROM LIST  ***/
	@JsonView(View.List.class)
	@RequestMapping(value = "/{idList}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ShoppingList> patchShoppingList(@PathVariable Integer idList,	@RequestBody ShoppingList shoppingList) throws ExceptionMessage {
		shoppingList = shoppingListService.patch(idList,shoppingList);
		if (shoppingList == null) throw new ExceptionMessage("ERROR IDLIST OR IDSHOPPINGLIST NOT FIND");
		 return new ResponseEntity<ShoppingList>(shoppingList, HttpStatus.OK);
	}
	
	
	/*** GET VERIFICATION ( DEBUG MODE)***/	
	@JsonView(View.List.class)
	@RequestMapping(value = "/{idList}", method = RequestMethod.PUT)
	public  ResponseEntity<ShoppingList> shoppingFindId(@PathVariable Integer idList) throws ExceptionMessage {
		ShoppingList shoppingList = shoppingListService.findById(idList.intValue());
		 return new ResponseEntity<ShoppingList>(shoppingList, HttpStatus.OK);

	}
	

	
	/*** DELETE ONE LIST : SAVE A LIST AND CREATE A LIST *****/
	@JsonView(View.List.class)
	@RequestMapping(value = "/{idList}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteShoppingList(@PathVariable Integer idList) throws ExceptionMessage 
	{
	 ShoppingList shoppingList = shoppingListService.findById(idList);
	 if (shoppingList.getIsActif() == false || shoppingList == null)
	 {
		 return new ResponseEntity<String>("LIST NOT FOUND", HttpStatus.BAD_REQUEST);
	 }
	 shoppingList.setIsActif(false);
	 shoppingListService.update(shoppingList);
	 
	 /*** REFONTE POUR UNE NOUVELLE LISTE ***/
	 shoppingList.setId(null);
	 shoppingList.setIsActif(true);
	 shoppingList.setListProducts(null);
	 shoppingList.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	 shoppingList.setIsFavor(null);
	 shoppingList.setIsClose(false);
	 shoppingList.setName("New"+shoppingList.getName());
	 shoppingListService.create(shoppingList);
	 return new ResponseEntity<String>("ELEMENT DELETED", HttpStatus.OK);
	}
	
	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}
	
	
}
