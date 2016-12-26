package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.CategoryNotFound;
import com.cheaplist.model.Category;

public interface CategoryService {
	
	public Category create(Category category);
	public Category delete(int id) throws CategoryNotFound;
	public List<Category> findAll();
	public Category update(Category category) throws CategoryNotFound;
	public Category findById(int id);

}
