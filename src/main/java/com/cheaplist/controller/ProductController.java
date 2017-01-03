package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Product;
import com.cheaplist.model.View;
import com.cheaplist.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	/*
	@Autowired
	private ShopValidator productValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("product-new", "product", new Product());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<Product> AdressFindAll() {
		ArrayList<Product> productList = (ArrayList<Product>) productService.findAll();
		for ( Product product  : productList)
		{
			 System.out.println(product.toString());
			System.out.println("Name :"+product.getId());
		}
		return productList;
				
	}
	
//	@JsonView(View.ProductShop.class)
	@RequestMapping(value="/findbyid/{id}")
	public Product newBrandproduct(@PathVariable Integer id) {
		Product product;
		if (id == null)
		{
			product = null;
		}
		else
		{
			System.out.println("Test Seb");
			 product = productService.findById(id.intValue());
			 System.out.println(product.toString());
		}
		
		return product;
				
	}
	
//	@JsonView(View.ProductSection.class)
	@RequestMapping(value="/section/{id}")
	public Product productSection(@PathVariable Integer id) {
		Product product;
		if (id == null)
		{
			product = null;
		}
		else
		{
			 product = productService.findById(id.intValue());
		}
		
		return product;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Product product,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("product-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New product "+product.getCity()+" was successfully created.";
		
		productService.create(product);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView productListPage() {
		ModelAndView mav = new ModelAndView("product-list");
		List<Product> productList = productService.findAll();
		mav.addObject("productList", productList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("product-edit");
		Product product = productService.findById(id);
		mav.addObject("product", product);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Product product,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ProductNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("product-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		productService.update(product);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ProductNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Product product = productService.delete(id);
		String message = "The product "+product.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
