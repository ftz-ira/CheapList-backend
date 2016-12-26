package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
