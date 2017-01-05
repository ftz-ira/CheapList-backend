package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ShopNotFound;
import com.cheaplist.model.Shop;
import com.cheaplist.repository.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Resource
	private ShopRepository shopRepository;

	@Override
	@Transactional
	public Shop create(Shop shop) {
		Shop createdShop = shop;
		return shopRepository.save(createdShop);
	}
	
	@Override
	@Transactional
	public Shop findById(int id) {
		return shopRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public Shop delete(int id) throws ShopNotFound {
		Shop deletedShop = shopRepository.findOne(id);
		
		if (deletedShop == null)
			throw new ShopNotFound();
		
		shopRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<Shop> findAll() {
		return shopRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public Shop update(Shop shop) throws ShopNotFound {
		Shop updatedShop = shopRepository.findOne(shop.getId());
		
		if (updatedShop == null)
			throw new ShopNotFound();

		updatedShop.setId(shop.getId());
		updatedShop.setName(shop.getName());
		updatedShop.setEmblem(shop.getEmblem());
		updatedShop.setCreatedDate(shop.getCreatedDate());
		updatedShop.setIsActive(shop.getIsActive());
		
		return updatedShop;
	}

	@Override
	public Shop findShopByIdgoogle(String idGoogle) {
		return shopRepository.findShopByIdgoogle(idGoogle);
	}

}
