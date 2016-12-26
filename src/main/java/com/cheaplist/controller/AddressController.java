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

import com.cheaplist.exception.AddressNotFound;
import com.cheaplist.model.Address;
import com.cheaplist.service.AddressService;


@RestController
@RequestMapping(value="/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;

	/*
	@Autowired
	private ShopValidator addressValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(addressValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("address-new", "address", new Address());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<Address> AdressFindAll() {
		ArrayList<Address> addressList = (ArrayList<Address>) addressService.findAll();
		for ( Address s  : addressList)
		{
			System.out.println("Name Brand:"+s.getCity()+"  Id Brand : "+s.getId());
		}
		return addressList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public Address newBrandaddress(@PathVariable Integer id) {
		Address UneAdresse;
		if (id == null)
		{
			UneAdresse = null;
		}
		else
		{
			System.out.println("Test Seb");
			 UneAdresse = addressService.findById(id.intValue());
		}
		
		return UneAdresse;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Address address,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("address-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New address "+address.getCity()+" was successfully created.";
		
		addressService.create(address);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView addressListPage() {
		ModelAndView mav = new ModelAndView("address-list");
		List<Address> addressList = addressService.findAll();
		mav.addObject("addressList", addressList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("address-edit");
		Address address = addressService.findById(id);
		mav.addObject("address", address);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Address address,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws AddressNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("address-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		addressService.update(address);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws AddressNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Address address = addressService.delete(id);
		String message = "The address "+address.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
