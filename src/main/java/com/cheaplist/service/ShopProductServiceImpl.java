package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.repository.ShopProductRepository;

@Service
public class ShopProductServiceImpl implements ShopProductService {
	
	@Resource
	private ShopProductRepository shopProductRepository;

	@Override
	@Transactional
	public ShopProduct create(ShopProduct shopProduct) {
		ShopProduct createdShopProduct = shopProduct;
		return shopProductRepository.save(createdShopProduct);
	}
	
	@Override
	@Transactional
	public ShopProduct findById(int id) {
		return shopProductRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public ShopProduct delete(int id) throws ExceptionMessage {
		ShopProduct deletedShopProduct = shopProductRepository.findOne(id);
		
		if (deletedShopProduct == null)
			throw new ExceptionMessage();
		
		shopProductRepository.delete(deletedShopProduct);
		return deletedShopProduct;
	}

	@Override
	@Transactional
	public List<ShopProduct> findAll() {
		return shopProductRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public ShopProduct update(ShopProduct shopProduct) throws ExceptionMessage {
		ShopProduct updatedShopProduct = shopProductRepository.findOne(shopProduct.getId());
		
		if (updatedShopProduct == null)
			throw new ExceptionMessage();

		updatedShopProduct.setId(shopProduct.getId());
		updatedShopProduct.setRatio(shopProduct.getRatio());
		updatedShopProduct.setPrice(shopProduct.getPrice());
		return updatedShopProduct;
	}
	
	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public ShopProduct patch(ShopProduct shopProduct) throws ExceptionMessage {
		ShopProduct updatedShopProduct = shopProductRepository.findPriceByProductShop(shopProduct.getProduct().getId(),shopProduct.getShop().getId());
		
		if (updatedShopProduct == null)
			throw new ExceptionMessage();
		
		updatedShopProduct.setPrice(shopProduct.getPrice());
		return updatedShopProduct;
	}

	@Override
	@Transactional
	public List<ShopProduct> findPriceByProduct(int idProduct) {
		return shopProductRepository.findPriceByProduct(idProduct);
	}
	
	@Override
	@Transactional
	public ShopProduct findPriceByProductShop(int idProduct, int idShop) {
		return shopProductRepository.findPriceByProductShop(idProduct,idShop);
	}


}
