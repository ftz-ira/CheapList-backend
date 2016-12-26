package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.ListProduct;

public interface ListProductRepository extends JpaRepository<ListProduct, Integer> {

}
