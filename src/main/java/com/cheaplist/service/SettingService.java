package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Setting;

public interface SettingService {
	
	public Setting create(Setting setting);
	public Setting delete(int id) throws ExceptionMessage;
	public List<Setting> findAll();
	public Setting update(Setting setting) throws ExceptionMessage;
	public Setting findById(int id);

}
