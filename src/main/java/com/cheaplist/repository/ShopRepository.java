package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

}
