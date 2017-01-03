package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.AddressNotFound;
import com.cheaplist.model.Address;
import com.cheaplist.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Resource
	private AddressRepository addressRepository;

	@Override
	@Transactional
	public Address create(Address address) {
		Address createdAddress = address;
		return addressRepository.save(createdAddress);
	}
	
	@Override
	@Transactional
	public Address findById(int id) {
		return addressRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=AddressNotFound.class)
	public Address delete(int id) throws AddressNotFound {
		Address deletedAddress = addressRepository.findOne(id);
		
		if (deletedAddress == null)
			throw new AddressNotFound();
		
		addressRepository.delete(deletedAddress);
		return deletedAddress;
	}

	@Override
	@Transactional
	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=AddressNotFound.class)
	public Address update(Address address) throws AddressNotFound {
		Address updatedAddress = addressRepository.findOne(address.getId());
		
		if (updatedAddress == null)
			throw new AddressNotFound();

		updatedAddress.setCity(address.getCity());
		updatedAddress.setStreetName(address.getStreetName());
		updatedAddress.setZipCode(address.getZipCode());
		updatedAddress.setLag(address.getLag());
		updatedAddress.setLng(address.getLng());
		return updatedAddress;
	}

}
