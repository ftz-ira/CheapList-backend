package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Product;

public interface ProductService {
	
	public Product create(Product product);
	public Product delete(int id) throws ExceptionMessage;
	public List<Product> findAll();
	public Product update(Product product) throws ExceptionMessage;
	public Product findById(int id);

}
