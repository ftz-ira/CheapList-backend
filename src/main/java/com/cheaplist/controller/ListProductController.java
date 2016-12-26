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

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.View;
import com.cheaplist.service.ListProductService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/listProduct")
public class ListProductController {
	
	@Autowired
	private ListProductService listProductService;

	/*
	@Autowired
	private ListProductValidator listProductValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(listProductValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newListProductPage() {
		ModelAndView mav = new ModelAndView("listProduct-new", "listProduct", new ListProduct());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<ListProduct> AdressFindAll() {
		ArrayList<ListProduct> listProductList = (ArrayList<ListProduct>) listProductService.findAll();
		for ( ListProduct listProduct  : listProductList)
		{
			 System.out.println(listProduct.toString());
			System.out.println("Name :"+listProduct.getId());
		}
		return listProductList;
				
	}
	

	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewListProduct(@ModelAttribute @Valid ListProduct listProduct,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("listProduct-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New listProduct "+listProduct.getCity()+" was successfully created.";
		
		listProductService.create(listProduct);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView listProductListPage() {
		ModelAndView mav = new ModelAndView("listProduct-list");
		List<ListProduct> listProductList = listProductService.findAll();
		mav.addObject("listProductList", listProductList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editListProductPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("listProduct-edit");
		ListProduct listProduct = listProductService.findById(id);
		mav.addObject("listProduct", listProduct);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editListProduct(@ModelAttribute @Valid ListProduct listProduct,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ListProductNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("listProduct-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "ListProduct was successfully updated.";

		listProductService.update(listProduct);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteListProduct(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ListProductNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		ListProduct listProduct = listProductService.delete(id);
		String message = "The listProduct "+listProduct.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
