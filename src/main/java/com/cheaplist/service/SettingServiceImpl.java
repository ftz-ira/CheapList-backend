package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
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
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Setting delete(int id) throws ExceptionMessage {
		Setting deletedSetting = settingRepository.findOne(id);
		
		if (deletedSetting == null)
			throw new ExceptionMessage();
		
		settingRepository.delete(deletedSetting);
		return deletedSetting;
	}

	@Override
	@Transactional
	public List<Setting> findAll() {
		return settingRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Setting update(Setting setting) throws ExceptionMessage {
		Setting updatedSetting = settingRepository.findOne(setting.getId());
		
		if (updatedSetting == null)
			throw new ExceptionMessage();

		updatedSetting.setId(setting.getId());
		updatedSetting.setName(setting.getName());
		
		
		
		return updatedSetting;
	}

}
