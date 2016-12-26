package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.ShopProduct;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {

}
