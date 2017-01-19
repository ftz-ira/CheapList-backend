package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Category;
import com.cheaplist.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Resource
	private CategoryRepository categoryRepository;

	@Override
	@Transactional
	public Category create(Category category) {
		Category createdCategory = category;		return categoryRepository.save(createdCategory);
	}
	
	@Override
	@Transactional
	public Category findById(int id) {
		return categoryRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Category delete(int id) throws ExceptionMessage {
		Category deletedCategory = categoryRepository.findOne(id);
		
		if (deletedCategory == null)
			throw new ExceptionMessage();
		
		categoryRepository.delete(deletedCategory);
		return deletedCategory;
	}

	@Override
	@Transactional
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Category update(Category category) throws ExceptionMessage {
		Category updatedCategory = categoryRepository.findOne(category.getId());
		
		if (updatedCategory == null)
			throw new ExceptionMessage();

		updatedCategory.setId(category.getId());
		updatedCategory.setName(category.getName());
		updatedCategory.setIsActive(category.getIsActive());
		
		return updatedCategory;
	}

}
