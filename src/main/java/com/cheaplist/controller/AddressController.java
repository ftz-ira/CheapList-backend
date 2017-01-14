package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.Address;
import com.cheaplist.service.AddressService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;

	
	/*** "Il inspire PLUS QU'il gere *****/
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

	
}
