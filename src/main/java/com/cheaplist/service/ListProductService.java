package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.model.ListProduct;

public interface ListProductService {
	
	public ListProduct create(ListProduct listProduct);
	public ListProduct delete(int id) throws ListProductNotFound;
	public List<ListProduct> findAll();
	public ListProduct update(ListProduct listProduct) throws ListProductNotFound;
	public ListProduct findById(int id);

	/*** CUSTOM METHOD ***/
	List<ListProduct> findProductsByList(int idList);
	ListProduct findProductByList(int idList, int idProduct);
	ListProduct patch(int idList, int idElement, ListProduct listProduct) throws ListProductNotFound;
	ListProduct createOneElement(int idShoppingList, int idproduct, int quantity) throws ListProductNotFound;
	public double findPrice(Integer idList, int idShop);
	public long findMissing(Integer idList, int idShop);
	public long countElement(Integer idList);
}
