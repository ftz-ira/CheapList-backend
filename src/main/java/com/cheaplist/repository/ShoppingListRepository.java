package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cheaplist.model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {
	
}
