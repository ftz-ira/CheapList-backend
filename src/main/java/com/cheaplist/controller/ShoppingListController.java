package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.model.ListProduct;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.model.View;
import com.cheaplist.service.ShoppingListService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping(value="/shoppingList")
public class ShoppingListController {
	
	@Autowired
	private ShoppingListService shoppingListService;

	/*
	@Autowired
	private ShopValidator shoppingListValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(shoppingListValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("shoppingList-new", "shoppingList", new ShoppingList());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<ShoppingList> AdressFindAll() {
		ArrayList<ShoppingList> shoppingListList = (ArrayList<ShoppingList>) shoppingListService.findAll();
		for ( ShoppingList shoppingList  : shoppingListList)
		{
			 System.out.println(shoppingList.toString());
			System.out.println("Name :"+shoppingList.getId());
		}
		return shoppingListList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public ShoppingList newShoppingListshoppingList(@PathVariable Integer id) {
		ShoppingList shoppingList;
		if (id == null)
		{
			shoppingList = null;
		}
		else
		{
			System.out.println("Test Seb");
			 shoppingList = shoppingListService.findById(id.intValue());
			 System.out.println(shoppingList.toString());
		}
		
		return shoppingList;
				
	}
	
	@JsonView(View.ListProduct.class)
	@RequestMapping(value="/findbyids/{id}")
	public ShoppingList  listProductId(@PathVariable Integer id) {
		ShoppingList  shoppingList;
		if (id == null)
		{
			shoppingList = null;
		}
		else
		{
			System.out.println("Test Seb");
			shoppingList = shoppingListService.findById(id.intValue());
			Set <ListProduct> Lists = shoppingList.getListProducts();
			for (ListProduct List : Lists)
			{
				System.out.println(List.getProduct().getName());
			}
			
			
			 System.out.println(shoppingList.toString());
		}
		
		return shoppingList;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid ShoppingList shoppingList,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("shoppingList-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New shoppingList "+shoppingList.getCity()+" was successfully created.";
		
		shoppingListService.create(shoppingList);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView shoppingListListPage() {
		ModelAndView mav = new ModelAndView("shoppingList-list");
		List<ShoppingList> shoppingListList = shoppingListService.findAll();
		mav.addObject("shoppingListList", shoppingListList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("shoppingList-edit");
		ShoppingList shoppingList = shoppingListService.findById(id);
		mav.addObject("shoppingList", shoppingList);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid ShoppingList shoppingList,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShoppingListNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("shoppingList-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		shoppingListService.update(shoppingList);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShoppingListNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		ShoppingList shoppingList = shoppingListService.delete(id);
		String message = "The shoppingList "+shoppingList.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
