package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.SettingNotFound;
import com.cheaplist.model.Setting;
import com.cheaplist.repository.SettingRepository;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Resource
	private SettingRepository settingRepository;

	@Override
	@Transactional
	public Setting create(Setting setting) {
		Setting createdSetting = setting;
		return settingRepository.save(createdSetting);
	}
	
	@Override
	@Transactional
	public Setting findById(int id) {
		return settingRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=SettingNotFound.class)
	public Setting delete(int id) throws SettingNotFound {
		Setting deletedSetting = settingRepository.findOne(id);
		
		if (deletedSetting == null)
			throw new SettingNotFound();
		
		settingRepository.delete(deletedSetting);
		return deletedSetting;
	}

	@Override
	@Transactional
	public List<Setting> findAll() {
		return settingRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=SettingNotFound.class)
	public Setting update(Setting setting) throws SettingNotFound {
		Setting updatedSetting = settingRepository.findOne(setting.getId());
		
		if (updatedSetting == null)
			throw new SettingNotFound();

		updatedSetting.setId(setting.getId());
		updatedSetting.setName(setting.getName());
		
		
		
		return updatedSetting;
	}

}
