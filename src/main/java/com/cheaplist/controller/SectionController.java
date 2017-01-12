package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.model.Category;
import com.cheaplist.model.Section;
import com.cheaplist.model.View;
import com.cheaplist.service.SectionService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/sections")
public class SectionController {
	
	@Autowired
	private SectionService sectionService;

	/***  READ ALL SECTION  ****/
	@JsonView(View.Section.class)
	@RequestMapping(value="",method=RequestMethod.GET)
	public List<Section> sectionAll() {
		ArrayList<Section> sectionList = (ArrayList<Section>) sectionService.findAll();
		for ( Section section : sectionList)
		{
			Set<Category> categories = section.getCategories();
			for (Category category : categories)
			{
				category.setProducts(null);
			}
		}
		return sectionList;
	
	}
	
	/***  READ ONE SECTION BY ID  ****/
	@JsonView(View.Section.class)
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Section sectionOne(@PathVariable Integer id) {
		Section section = sectionService.findById(id.intValue());		
		return section;
				
	}
	
	
	/***  READ ALL CATEGORIES BY ID SECTION  ****/
	@JsonView(View.SectionCategory.class)
	@RequestMapping(value="/{id}/categories",method=RequestMethod.GET)
	public Section sectionAllCategories(@PathVariable Integer id) {
		Section section = sectionService.findById(id.intValue());
		return section;				
	}
	
	
}
