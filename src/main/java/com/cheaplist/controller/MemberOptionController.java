package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.model.MemberOption;
import com.cheaplist.service.MemberOptionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/memberOption")
public class MemberOptionController {
	
	@Autowired
	private MemberOptionService memberOptionService;

	/*
	@Autowired
	private ShopValidator memberOptionValidator;
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(memberOptionValidator);
	}*/

/*	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("memberOption-new", "memberOption", new MemberOption());
		return mav;
	}
	*/
	@RequestMapping(value="/findall")
	public List<MemberOption> AdressFindAll() {
		ArrayList<MemberOption> memberOptionList = (ArrayList<MemberOption>) memberOptionService.findAll();
		for ( MemberOption memberOption  : memberOptionList)
		{
			 System.out.println(memberOption.toString());
			System.out.println("Name :"+memberOption.getId());
		}
		return memberOptionList;
				
	}
	
	@RequestMapping(value="/findbyid/{id}")
	public MemberOption newBrandmemberOption(@PathVariable Integer id) {
		MemberOption memberOption;
		if (id == null)
		{
			memberOption = null;
		}
		else
		{
			System.out.println("Test Seb");
			 memberOption = memberOptionService.findById(id.intValue());
			 System.out.println(memberOption.getMember());
		}
		System.out.println("Test Seby");
		
		//return memberOption;
			return null; // Temporaire	
	}
	/*
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid MemberOption memberOption,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("memberOption-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New memberOption "+memberOption.getCity()+" was successfully created.";
		
		memberOptionService.create(memberOption);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView memberOptionListPage() {
		ModelAndView mav = new ModelAndView("memberOption-list");
		List<MemberOption> memberOptionList = memberOptionService.findAll();
		mav.addObject("memberOptionList", memberOptionList);
		return mav;
	}
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("memberOption-edit");
		MemberOption memberOption = memberOptionService.findById(id);
		mav.addObject("memberOption", memberOption);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid MemberOption memberOption,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws MemberOptionNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("memberOption-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Shop was successfully updated.";

		memberOptionService.update(memberOption);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws MemberOptionNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		MemberOption memberOption = memberOptionService.delete(id);
		String message = "The memberOption "+memberOption.getCity()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	*/
	
}
