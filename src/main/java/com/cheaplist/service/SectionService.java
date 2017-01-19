package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Section;

public interface SectionService {
	
	public Section create(Section section);
	public Section delete(int id) throws ExceptionMessage;
	public List<Section> findAll();
	public Section update(Section section) throws ExceptionMessage;
	public Section findById(int id);

}
