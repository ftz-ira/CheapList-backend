package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.BrandNotFound;
import com.cheaplist.model.Brand;

public interface BrandService {
	
	public Brand create(Brand brand);
	public Brand delete(int id) throws BrandNotFound;
	public List<Brand> findAll();
	public Brand update(Brand brand) throws BrandNotFound;
	public Brand findById(int id);

}
