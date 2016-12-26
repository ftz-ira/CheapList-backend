package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.BrandNotFound;
import com.cheaplist.model.Brand;
import com.cheaplist.repository.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {
	
	@Resource
	private BrandRepository brandRepository;

	@Override
	@Transactional
	public Brand create(Brand brand) {
		Brand createdBrand = brand;
		return brandRepository.save(createdBrand);
	}
	
	@Override
	@Transactional
	public Brand findById(int id) {
		return brandRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=BrandNotFound.class)
	public Brand delete(int id) throws BrandNotFound {
		Brand deletedShop = brandRepository.findOne(id);
		
		if (deletedShop == null)
			throw new BrandNotFound();
		
		brandRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=BrandNotFound.class)
	public Brand update(Brand brand) throws BrandNotFound {
		Brand updatedBrand = brandRepository.findOne(brand.getId());
		updatedBrand.setId(brand.getId());
		updatedBrand.setName(brand.getName());
		updatedBrand.setCreatedDate(brand.getCreatedDate());
		updatedBrand.setUpdateDate(brand.getUpdateDate());
		updatedBrand.setIsActive(brand.getIsActive());
		return updatedBrand;
	}

}
