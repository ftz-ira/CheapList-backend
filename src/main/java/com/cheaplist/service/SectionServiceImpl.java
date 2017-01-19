package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Section;
import com.cheaplist.repository.SectionRepository;

@Service
public class SectionServiceImpl implements SectionService {
	
	@Resource
	private SectionRepository sectionRepository;

	@Override
	@Transactional
	public Section create(Section section) {
		Section createdSection = section;
		return sectionRepository.save(createdSection);
	}
	
	@Override
	@Transactional
	public Section findById(int id) {
		return sectionRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Section delete(int id) throws ExceptionMessage {
		Section deletedSection = sectionRepository.findOne(id);
		
		if (deletedSection == null)
			throw new ExceptionMessage();
		
		sectionRepository.delete(deletedSection);
		return deletedSection;
	}

	@Override
	@Transactional
	public List<Section> findAll() {
		return sectionRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public Section update(Section section) throws ExceptionMessage {
		Section updatedSection = sectionRepository.findOne(section.getId());
		
		if (updatedSection == null)
			throw new ExceptionMessage();

		updatedSection.setId(section.getId());
		updatedSection.setName(section.getName());
		updatedSection.setDescription(section.getDescription());
		
		
		
		return updatedSection;
	}

}
