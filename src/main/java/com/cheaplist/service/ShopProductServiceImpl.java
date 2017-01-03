package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ShopProductNotFound;
import com.cheaplist.model.Product;
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
	@Transactional(rollbackFor=ShopProductNotFound.class)
	public ShopProduct delete(int id) throws ShopProductNotFound {
		ShopProduct deletedShopProduct = shopProductRepository.findOne(id);
		
		if (deletedShopProduct == null)
			throw new ShopProductNotFound();
		
		shopProductRepository.delete(deletedShopProduct);
		return deletedShopProduct;
	}

	@Override
	@Transactional
	public List<ShopProduct> findAll() {
		return shopProductRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopProductNotFound.class)
	public ShopProduct update(ShopProduct shopProduct) throws ShopProductNotFound {
		ShopProduct updatedShopProduct = shopProductRepository.findOne(shopProduct.getId());
		
		if (updatedShopProduct == null)
			throw new ShopProductNotFound();

		updatedShopProduct.setId(shopProduct.getId());
		updatedShopProduct.setRatio(shopProduct.getRatio());
		updatedShopProduct.setPrice(shopProduct.getPrice());
		return updatedShopProduct;
	}

	@Override
	@Transactional
	public List<ShopProduct> findPriceByProduct(int idProduct) {
		return shopProductRepository.findPriceByProduct(idProduct);
	}
	
	/*public List<ShopProduct> findPriceByProduct(int idProduct)
	{
		return 
	}
	*/

}
