package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ShopProduct;

public interface ShopProductService {
	
	public ShopProduct create(ShopProduct shopProduct);
	public ShopProduct delete(int id) throws ExceptionMessage;
	public List<ShopProduct> findAll();
	public ShopProduct update(ShopProduct shopProduct) throws ExceptionMessage;
	public ShopProduct findById(int id);
	List<ShopProduct> findPriceByProduct(int idProduct);
	ShopProduct findPriceByProductShop(int idProduct, int idShop);
	ShopProduct patch(ShopProduct shopProduct) throws ExceptionMessage;

}
