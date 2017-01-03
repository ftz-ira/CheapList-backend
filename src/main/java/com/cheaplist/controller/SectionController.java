package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Section;
import com.cheaplist.model.View;
import com.cheaplist.service.SectionService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/sections")
public class SectionController {
	
	@Autowired
	private SectionService sectionService;

	@JsonView(View.Section.class)
	@RequestMapping(value="",method=RequestMethod.GET)
	public List<Section> SectionAll() {
		ArrayList<Section> sectionList = (ArrayList<Section>) sectionService.findAll();
		return sectionList;
	
	}
	
	
	@JsonView(View.Section.class)
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Section SectionOne(@PathVariable Integer id) {
		Section section = sectionService.findById(id.intValue());		
		return section;
				
	}
	
	
	/***  READ ONE SECTION ALL CATEGORIES              ****/
	@JsonView(View.SectionCategory.class)
	@RequestMapping(value="/{id}/categories/",method=RequestMethod.GET)
	public Section SectionAllCategories(@PathVariable Integer id) {
		Section section = sectionService.findById(id.intValue());
		return section;				
	}
	
	
}
