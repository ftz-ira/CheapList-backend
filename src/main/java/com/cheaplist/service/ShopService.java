package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Shop;

public interface ShopService {
	
	public Shop create(Shop shop);
	public Shop delete(int id) throws ExceptionMessage;
	public List<Shop> findAll();
	public Shop update(Shop shop) throws ExceptionMessage;
	public Shop findById(int id);
	Shop findShopByIdgoogle(String idGoogle);
}
