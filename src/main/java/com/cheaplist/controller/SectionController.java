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

	/*
	@Autowired
	private ShopValidator sectionValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(sectionValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("section-new", "section", new Section());
		return mav;
	}
	*/
	@JsonView(View.SectionProduct.class)
	@RequestMapping(value="/findall")
	public List<Section> AdressFindAll() {
		ArrayList<Section> sectionList = (ArrayList<Section>) sectionService.findAll();
		for ( Section section  : sectionList)
		{
			 System.out.println(section.toString());
			System.out.println("Name :"+section.getId());
		}
		return sectionList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public Section newBrandsection(@PathVariable Integer id) {
		Section section;
		if (id == null)
		{
			section = null;
		}
		else
		{
			System.out.println("Test Seb");
			 section = sectionService.findById(id.intValue());
			 System.out.println(section.toString());
		}
		
		return section;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Section section,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("section-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New section "+section.getCity()+" was successfully created.";
		
		sectionService.create(section);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView sectionListPage() {
		ModelAndView mav = new ModelAndView("section-list");
		List<Section> sectionList = sectionService.findAll();
		mav.addObject("sectionList", sectionList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("section-edit");
		Section section = sectionService.findById(id);
		mav.addObject("section", section);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Section section,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws SectionNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("section-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		sectionService.update(section);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws SectionNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Section section = sectionService.delete(id);
		String message = "The section "+section.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
