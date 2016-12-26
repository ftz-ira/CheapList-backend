package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.AddressNotFound;
import com.cheaplist.model.Address;

public interface AddressService {
	
	public Address create(Address address);
	public Address delete(int id) throws AddressNotFound;
	public List<Address> findAll();
	public Address update(Address address) throws AddressNotFound;
	public Address findById(int id);

}
