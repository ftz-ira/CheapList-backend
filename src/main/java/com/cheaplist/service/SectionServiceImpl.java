package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.SectionNotFound;
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
	@Transactional(rollbackFor=SectionNotFound.class)
	public Section delete(int id) throws SectionNotFound {
		Section deletedSection = sectionRepository.findOne(id);
		
		if (deletedSection == null)
			throw new SectionNotFound();
		
		sectionRepository.delete(deletedSection);
		return deletedSection;
	}

	@Override
	@Transactional
	public List<Section> findAll() {
		return sectionRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=SectionNotFound.class)
	public Section update(Section section) throws SectionNotFound {
		Section updatedSection = sectionRepository.findOne(section.getId());
		
		if (updatedSection == null)
			throw new SectionNotFound();

		updatedSection.setId(section.getId());
		updatedSection.setName(section.getName());
		updatedSection.setDescription(section.getDescription());
		
		
		
		return updatedSection;
	}

}
