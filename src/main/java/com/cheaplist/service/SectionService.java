package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.SectionNotFound;
import com.cheaplist.model.Section;

public interface SectionService {
	
	public Section create(Section section);
	public Section delete(int id) throws SectionNotFound;
	public List<Section> findAll();
	public Section update(Section section) throws SectionNotFound;
	public Section findById(int id);

}
