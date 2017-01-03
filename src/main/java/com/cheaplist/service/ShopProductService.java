package com.cheaplist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cheaplist.exception.ShopProductNotFound;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.repository.ShopProductRepository;

public interface ShopProductService {
	
	public ShopProduct create(ShopProduct shopProduct);
	public ShopProduct delete(int id) throws ShopProductNotFound;
	public List<ShopProduct> findAll();
	public ShopProduct update(ShopProduct shopProduct) throws ShopProductNotFound;
	public ShopProduct findById(int id);
	List<ShopProduct> findPriceByProduct(int idProduct);

}
