package com.cheaplist.service;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Product;
import com.cheaplist.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Resource
	private ProductRepository productRepository;

	@Override
	@Transactional
	public Product create(Product product) {
		Product createdProduct = product;
		return productRepository.save(createdProduct);
	}
	
	@Override
	@Transactional
	public Product findById(int id) {
		return productRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Product delete(int id) throws ExceptionMessage {
		Product deletedProduct = productRepository.findOne(id);
		
		if (deletedProduct == null)
			throw new ExceptionMessage();
		
		productRepository.delete(deletedProduct);
		return deletedProduct;
	}

	@Override
	@Transactional
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Product update(Product product) throws ExceptionMessage {
		Product updatedProduct = productRepository.findOne(product.getId());
		
		if (updatedProduct == null)
			throw new ExceptionMessage();

		updatedProduct.setId(product.getId());
		updatedProduct.setName(product.getName());
		updatedProduct.setUnitName(product.getUnitName());
		updatedProduct.setEan(product.getEan());
		updatedProduct.setVolume(product.getVolume());
		
		
		
		return updatedProduct;
	}

	@Override
	public Product findByEan(BigInteger Ean) {
		return productRepository.findProductsByEan(Ean);

	}

}
