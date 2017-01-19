package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Category;

public interface CategoryService {
	
	public Category create(Category category);
	public Category delete(int id) throws ExceptionMessage;
	public List<Category> findAll();
	public Category update(Category category) throws ExceptionMessage;
	public Category findById(int id);

}
