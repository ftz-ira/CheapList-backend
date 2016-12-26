package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
