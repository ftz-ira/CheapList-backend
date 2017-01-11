package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ShoppingListNotFound;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.repository.ShoppingListRepository;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

	@Resource
	private ShoppingListRepository shoppingListRepository;

	@Override
	@Transactional
	public ShoppingList create(ShoppingList shoppingList) {
		ShoppingList createdShoppingList = shoppingList;
		return shoppingListRepository.save(createdShoppingList);
	}

	@Override
	@Transactional
	public ShoppingList findById(int id) {
		return shoppingListRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ShoppingListNotFound.class)
	public ShoppingList delete(int id) throws ShoppingListNotFound {
		ShoppingList deletedShop = shoppingListRepository.findOne(id);

		if (deletedShop == null)
			throw new ShoppingListNotFound();

		shoppingListRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<ShoppingList> findAll() {
		return shoppingListRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ShoppingListNotFound.class)
	public ShoppingList update(ShoppingList shoppingList) throws ShoppingListNotFound {
		ShoppingList updatedShoppingList = shoppingListRepository.findOne(shoppingList.getId());
		updatedShoppingList.setId(shoppingList.getId());
		updatedShoppingList.setName(shoppingList.getName());
		updatedShoppingList.setIsActif(shoppingList.getIsActif());
		updatedShoppingList.setIsClose(shoppingList.getIsClose());
		updatedShoppingList.setCreatedDate(shoppingList.getCreatedDate());

		return updatedShoppingList;
	}

	@Override
	public ShoppingList patch(Integer idList, ShoppingList newshoppingList) throws ShoppingListNotFound {
		ShoppingList oldshoppingList = shoppingListRepository.findOne(idList);
		if (oldshoppingList == null) {
			throw new ShoppingListNotFound();
		}
		
		
		return oldshoppingList;
	}

}
