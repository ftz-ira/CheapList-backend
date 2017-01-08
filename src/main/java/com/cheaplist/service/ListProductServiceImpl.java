package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.repository.ListProductRepository;

@Service
public class ListProductServiceImpl implements ListProductService {
	
	@Resource
	private ListProductRepository listProductRepository;

	@Override
	@Transactional
	public ListProduct create(ListProduct listProduct) {
		ListProduct createdListProduct = listProduct;
		return listProductRepository.save(createdListProduct);
	}
	
	@Override
	@Transactional
	public ListProduct findById(int id) {
		return listProductRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ListProductNotFound.class)
	public ListProduct delete(int id) throws ListProductNotFound {
		ListProduct deletedListProduct = listProductRepository.findOne(id);
		
		if (deletedListProduct == null)
			throw new ListProductNotFound();
		
		listProductRepository.delete(deletedListProduct);
		return deletedListProduct;
	}

	@Override
	@Transactional
	public List<ListProduct> findAll() {
		return listProductRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ListProductNotFound.class)
	public ListProduct update(ListProduct listProduct) throws ListProductNotFound {
		ListProduct updatedListProduct = listProductRepository.findOne(listProduct.getId());
		
		if (updatedListProduct == null)
			throw new ListProductNotFound();

		updatedListProduct.setId(listProduct.getId());
		updatedListProduct.setProductQuantity(listProduct.getProductQuantity());
		return updatedListProduct;
	}
	
	@Override
	@Transactional
	public List<ListProduct> findProductsByList(int idList) {
		return listProductRepository.findProductsByList(idList);
	}

}
