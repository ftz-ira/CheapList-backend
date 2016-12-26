package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
