package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ProductNotFound;
import com.cheaplist.model.Product;

public interface ProductService {
	
	public Product create(Product product);
	public Product delete(int id) throws ProductNotFound;
	public List<Product> findAll();
	public Product update(Product product) throws ProductNotFound;
	public Product findById(int id);

}
