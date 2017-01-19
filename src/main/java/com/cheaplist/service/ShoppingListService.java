package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ShoppingList;

public interface ShoppingListService {
	
	public ShoppingList create(ShoppingList shoppingList);
	public ShoppingList delete(int id) throws ExceptionMessage;
	public List<ShoppingList> findAll();
	public ShoppingList update(ShoppingList shoppingList) throws ExceptionMessage;
	public ShoppingList findById(int id);
	public ShoppingList patch(Integer idList, ShoppingList shoppingList) throws ExceptionMessage;

}
