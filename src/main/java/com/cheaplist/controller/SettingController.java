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

import com.cheaplist.exception.SettingNotFound;
import com.cheaplist.model.Setting;
import com.cheaplist.service.SettingService;


@RestController
@RequestMapping(value="/setting")
public class SettingController {
	
	@Autowired
	private SettingService settingService;

	/*
	@Autowired
	private ShopValidator settingValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(settingValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("setting-new", "setting", new Setting());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<Setting> AdressFindAll() {
		ArrayList<Setting> settingList = (ArrayList<Setting>) settingService.findAll();
		for ( Setting setting  : settingList)
		{
			 System.out.println(setting.toString());
			System.out.println("Name :"+setting.getId());
		}
		return settingList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public Setting newBrandsetting(@PathVariable Integer id) {
		Setting setting;
		if (id == null)
		{
			setting = null;
		}
		else
		{
			System.out.println("Test Seb");
			 setting = settingService.findById(id.intValue());
			 System.out.println(setting.toString());
		}
		
		return setting;
				
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Setting setting,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("setting-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New setting "+setting.getCity()+" was successfully created.";
		
		settingService.create(setting);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView settingListPage() {
		ModelAndView mav = new ModelAndView("setting-list");
		List<Setting> settingList = settingService.findAll();
		mav.addObject("settingList", settingList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("setting-edit");
		Setting setting = settingService.findById(id);
		mav.addObject("setting", setting);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Setting setting,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws SettingNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("setting-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		settingService.update(setting);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws SettingNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Setting setting = settingService.delete(id);
		String message = "The setting "+setting.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
