package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ListProduct;

public interface ListProductService {
	
	public ListProduct create(ListProduct listProduct);
	public ListProduct delete(int id) throws ExceptionMessage;
	public List<ListProduct> findAll();
	public ListProduct update(ListProduct listProduct) throws ExceptionMessage;
	public ListProduct findById(int id);

	/*** CUSTOM METHOD ***/
	List<ListProduct> findProductsByList(int idList);
	List<ListProduct> findElementByListBtProduct(int idList,int idProduct);
	ListProduct findProductByList(int idList, int idProduct);
	ListProduct patch(int idList, int idElement, ListProduct listProduct) throws ExceptionMessage;
	ListProduct createOneElement(int idShoppingList, int idproduct, int quantity) throws ExceptionMessage;
	
	public double findPrice(Integer idList, int idShop);
	public long findMissing(Integer idList, int idShop);
	public long countElement(Integer idList);

}
