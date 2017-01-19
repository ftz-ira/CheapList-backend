package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Address;

public interface AddressService {
	
	public Address create(Address address);
	public Address delete(int id) throws ExceptionMessage;
	public List<Address> findAll();
	public Address update(Address address) throws ExceptionMessage;
	public Address findById(int id);

}
