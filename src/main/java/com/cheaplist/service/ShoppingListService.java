package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ShoppingListNotFound;
import com.cheaplist.model.ShoppingList;

public interface ShoppingListService {
	
	public ShoppingList create(ShoppingList shoppingList);
	public ShoppingList delete(int id) throws ShoppingListNotFound;
	public List<ShoppingList> findAll();
	public ShoppingList update(ShoppingList shoppingList) throws ShoppingListNotFound;
	public ShoppingList findById(int id);

}
