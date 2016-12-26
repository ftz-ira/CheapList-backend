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

import com.cheaplist.exception.ShopProductNotFound;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.service.ShopProductService;


@RestController
@RequestMapping(value="/shopProduct")
public class ShopProductController {
	
	@Autowired
	private ShopProductService shopProductService;

	/*
	@Autowired
	private ShopProductValidator shopProductValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(shopProductValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopProductPage() {
		ModelAndView mav = new ModelAndView("shopProduct-new", "shopProduct", new ShopProduct());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<ShopProduct> AdressFindAll() {
		ArrayList<ShopProduct> shopProductList = (ArrayList<ShopProduct>) shopProductService.findAll();
		for ( ShopProduct shopProduct  : shopProductList)
		{
			 System.out.println(shopProduct.toString());
			System.out.println("Name :"+shopProduct.getId());
		}
		return shopProductList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public ShopProduct newBrandshopProduct(@PathVariable Integer id) {
		ShopProduct shopProduct;
		if (id == null)
		{
			shopProduct = null;
		}
		else
		{
			System.out.println("Test Seb");
			 shopProduct = shopProductService.findById(id.intValue());
			 System.out.println(shopProduct.toString());
		}
		
		return shopProduct;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShopProduct(@ModelAttribute @Valid ShopProduct shopProduct,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("shopProduct-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New shopProduct "+shopProduct.getCity()+" was successfully created.";
		
		shopProductService.create(shopProduct);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView shopProductListPage() {
		ModelAndView mav = new ModelAndView("shopProduct-list");
		List<ShopProduct> shopProductList = shopProductService.findAll();
		mav.addObject("shopProductList", shopProductList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopProductPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("shopProduct-edit");
		ShopProduct shopProduct = shopProductService.findById(id);
		mav.addObject("shopProduct", shopProduct);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShopProduct(@ModelAttribute @Valid ShopProduct shopProduct,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShopProductNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("shopProduct-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "ShopProduct was successfully updated.";

		shopProductService.update(shopProduct);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShopProduct(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShopProductNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		ShopProduct shopProduct = shopProductService.delete(id);
		String message = "The shopProduct "+shopProduct.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
