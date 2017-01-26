package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Shop;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.repository.ShoppingListRepository;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

	@Resource
	private ShoppingListRepository shoppingListRepository;
	
	@Autowired
	private ShopService shopService;

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
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ShoppingList delete(int id) throws ExceptionMessage {
		ShoppingList deletedShop = shoppingListRepository.findOne(id);

		if (deletedShop == null)
			throw new ExceptionMessage();

		shoppingListRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<ShoppingList> findAll() {
		return shoppingListRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ShoppingList update(ShoppingList shoppingList) throws ExceptionMessage {
		ShoppingList updatedShoppingList = shoppingListRepository.findOne(shoppingList.getId());
		updatedShoppingList.setId(shoppingList.getId());
		updatedShoppingList.setName(shoppingList.getName());
		updatedShoppingList.setIsActif(shoppingList.getIsActif());
		updatedShoppingList.setIsClose(shoppingList.getIsClose());
		updatedShoppingList.setUpdatedDate(shoppingList.getUpdatedDate());

		return updatedShoppingList;
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ShoppingList patch(Integer idList, ShoppingList shoppingList) throws ExceptionMessage {
		ShoppingList oldshoppingList = shoppingListRepository.findOne(idList);
		if (oldshoppingList == null) {
			throw new ExceptionMessage();
		}
		if (shoppingList.getIsActif() != null)
			oldshoppingList.setIsActif(shoppingList.getIsActif());
		if (shoppingList.getIsClose() != null)
			oldshoppingList.setIsClose(shoppingList.getIsClose());
		if (shoppingList.getIsFavor() != null)
			oldshoppingList.setIsFavor(shoppingList.getIsFavor());
		if (shoppingList.getIsDone() != null)
			oldshoppingList.setIsDone(shoppingList.getIsDone());
		if (shoppingList.getName() != null)
			oldshoppingList.setName(shoppingList.getName());
		
		if (shoppingList.getShop() != null)
			{
			int idShop = shoppingList.getShop().getId();
			Shop shop = shopService.findById(idShop);
			if (shop == null) throw new ExceptionMessage("ERROR IdShop Introuvable");
			oldshoppingList.setShop(shop);
			}

		return oldshoppingList;
	}

}
