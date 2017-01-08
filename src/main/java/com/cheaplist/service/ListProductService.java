package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.ShopProduct;

public interface ListProductService {
	
	public ListProduct create(ListProduct listProduct);
	public ListProduct delete(int id) throws ListProductNotFound;
	public List<ListProduct> findAll();
	public ListProduct update(ListProduct listProduct) throws ListProductNotFound;
	public ListProduct findById(int id);
	List<ListProduct> findProductsByList(int idList);

}
