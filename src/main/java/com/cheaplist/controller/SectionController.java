package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cheaplist.exception.SectionNotFound;
import com.cheaplist.model.Section;
import com.cheaplist.model.View;
import com.cheaplist.service.SectionService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/section")
public class SectionController {
	
	@Autowired
	private SectionService sectionService;

	@JsonView(View.SectionCategory.class)
	@RequestMapping(value="/gets/")
	public List<Section> AdressFindAll() {
		ArrayList<Section> sectionList = (ArrayList<Section>) sectionService.findAll();
		for ( Section section  : sectionList)
		{
			 System.out.println(section.toString());
			System.out.println("Name :"+section.getId());
		}
		return sectionList;
				
	}
	
	@JsonView(View.SectionCategory.class)
	@RequestMapping(value="/get/{id}")
	public Section newBrandsection(@PathVariable Integer id) {
		Section section;
		section = sectionService.findById(id.intValue());
		return section;				
	}
	
	
}
