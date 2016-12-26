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

import com.cheaplist.exception.BrandNotFound;
import com.cheaplist.model.Brand;
import com.cheaplist.service.BrandService;


@RestController
@RequestMapping(value="/brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;

	/*
	@Autowired
	private ShopValidator brandValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(brandValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("brand-new", "brand", new Brand());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<Brand> AdressFindAll() {
		ArrayList<Brand> brandList = (ArrayList<Brand>) brandService.findAll();
		for ( Brand brand  : brandList)
		{
			 System.out.println(brand.toString());
			System.out.println("Name :"+brand.getId());
		}
		return brandList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public Brand newBrandbrand(@PathVariable Integer id) {
		Brand brand;
		if (id == null)
		{
			brand = null;
		}
		else
		{
			System.out.println("Test Seb");
			 brand = brandService.findById(id.intValue());
			 System.out.println(brand.toString());
		}
		
		return brand;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Brand brand,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("brand-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New brand "+brand.getCity()+" was successfully created.";
		
		brandService.create(brand);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView brandListPage() {
		ModelAndView mav = new ModelAndView("brand-list");
		List<Brand> brandList = brandService.findAll();
		mav.addObject("brandList", brandList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("brand-edit");
		Brand brand = brandService.findById(id);
		mav.addObject("brand", brand);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Brand brand,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws BrandNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("brand-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		brandService.update(brand);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws BrandNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Brand brand = brandService.delete(id);
		String message = "The brand "+brand.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
