package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Shop;
import com.cheaplist.model.View;
import com.cheaplist.service.ShopService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/shop")
public class ShopController {
	
	@Autowired
	private ShopService shopService;

	/*
	@Autowired
	private ShopValidator shopValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(shopValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("shop-new", "shop", new Shop());
		return mav;
	}
	*/
//	@JsonView(View.ShopAddress.class)
	@RequestMapping(value="/findall")
	public List<Shop> AdressFindAll() {
		ArrayList<Shop> shopList = (ArrayList<Shop>) shopService.findAll();
		for ( Shop shop  : shopList)
		{
			 System.out.println(shop.toString());
			System.out.println("Name :"+shop.getId());
		}
		return shopList;
				
	}
	
	//@JsonView(View.ShopAddress.class)
	@RequestMapping(value="/findbyid/{id}")
	public Shop newBrandshop(@PathVariable Integer id) {
		Shop shop;
		 shop = shopService.findById(id.intValue());
	
		return shop;
				
	}
	
	
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Shop shop,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("shop-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New shop "+shop.getCity()+" was successfully created.";
		
		shopService.create(shop);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView shopListPage() {
		ModelAndView mav = new ModelAndView("shop-list");
		List<Shop> shopList = shopService.findAll();
		mav.addObject("shopList", shopList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("shop-edit");
		Shop shop = shopService.findById(id);
		mav.addObject("shop", shop);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Shop shop,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShopNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("shop-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		shopService.update(shop);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShopNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Shop shop = shopService.delete(id);
		String message = "The shop "+shop.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
