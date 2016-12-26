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

import com.cheaplist.exception.CategoryNotFound;
import com.cheaplist.model.Category;
import com.cheaplist.service.CategoryService;


@RestController
@RequestMapping(value="/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	/*
	@Autowired
	private ShopValidator categoryValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(categoryValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("category-new", "category", new Category());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<Category> AdressFindAll() {
		ArrayList<Category> categoryList = (ArrayList<Category>) categoryService.findAll();
		for ( Category category  : categoryList)
		{
			 System.out.println(category.toString());
			System.out.println("Name :"+category.getId());
		}
		return categoryList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public Category newBrandcategory(@PathVariable Integer id) {
		Category category;
		if (id == null)
		{
			category = null;
		}
		else
		{
			System.out.println("Test Seb");
			 category = categoryService.findById(id.intValue());
			 System.out.println(category.toString());
		}
		
		return category;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Category category,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("category-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New category "+category.getCity()+" was successfully created.";
		
		categoryService.create(category);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView categoryListPage() {
		ModelAndView mav = new ModelAndView("category-list");
		List<Category> categoryList = categoryService.findAll();
		mav.addObject("categoryList", categoryList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("category-edit");
		Category category = categoryService.findById(id);
		mav.addObject("category", category);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Category category,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws CategoryNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("category-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		categoryService.update(category);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws CategoryNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Category category = categoryService.delete(id);
		String message = "The category "+category.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
