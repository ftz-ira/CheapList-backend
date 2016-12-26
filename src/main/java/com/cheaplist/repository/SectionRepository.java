package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.Section;

public interface SectionRepository extends JpaRepository<Section, Integer> {

}
