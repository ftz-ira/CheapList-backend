package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheaplist.model.Setting;

public interface SettingRepository extends JpaRepository<Setting, Integer> {

}
