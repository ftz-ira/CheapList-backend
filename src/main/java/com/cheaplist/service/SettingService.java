package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.SettingNotFound;
import com.cheaplist.model.Setting;

public interface SettingService {
	
	public Setting create(Setting setting);
	public Setting delete(int id) throws SettingNotFound;
	public List<Setting> findAll();
	public Setting update(Setting setting) throws SettingNotFound;
	public Setting findById(int id);

}
