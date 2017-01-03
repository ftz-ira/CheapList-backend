package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.model.Member;
import com.cheaplist.model.View;
import com.cheaplist.service.MemberService;
import com.cheaplist.validator.MemberValidator;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/members")
public class MemberController {

	@Autowired
	private MemberService memberService;

	
	 @Autowired private MemberValidator memberValidator;
			
	 @InitBinder private void initBinder(WebDataBinder binder) {
	  binder.setValidator(memberValidator); }
	 
	 /*****  CREATE METHODE     ******/
	 @RequestMapping(value="/",method=RequestMethod.PUT,consumes="application/json",produces="application/json")
	 List<ObjectError> createNewShop(@RequestBody Member member,BindingResult result) 			
	 { 
		 	System.out.println("Test Member : "+member.getName());
		 	memberValidator.validate(member,result);		 	
		 	if (result.hasErrors()) return result.getAllErrors();	
		 	member =memberService.create(member);	
		 	System.out.println(member.getId());
		 	return null;
	 }
	 
	 
	 /*****  READ ALL METHODE  : ALL MEMBER  ******/
	//@JsonView(View.MemberIdentity.class)
	@RequestMapping(method=RequestMethod.GET)
	public List<Member> identityFindAll() {
		ArrayList<Member> memberList = (ArrayList<Member>) memberService.findAll();
		for (Member member : memberList) {
			System.out.println(member.toString());
			System.out.println("Name :" + member.getName() + "  Id : " + member.getEmail());
		}
		return memberList;

	}
	
	 /*****  READ ONE METHODE : ONE MEMBER   ******/
	//@JsonView(View.MemberIdentity.class)
	@RequestMapping(value = "/{id}")
	public Member identityFindId(@PathVariable Integer id) {

		Member member;
		member = memberService.findById(id.intValue());
		return member;

	}

	
	/*****  READ ONE MEMBER : ALL LIST BY MEMBER   ******/
	//@JsonView(View.MemberList.class)
	@RequestMapping(value = "/lists/{id}")
	public Member listFindid(@PathVariable Integer id) {
		Member member;
		member = memberService.findById(id.intValue());
		return member;
	}


	/*
	  @RequestMapping(value="/create", method=RequestMethod.POST) public
	  ModelAndView createNewShop(@ModelAttribute @Valid Member member,
	  BindingResult result, final RedirectAttributes redirectAttributes) {
	  
	  if (result.hasErrors()) return new ModelAndView("member-new");
	  
	  ModelAndView mav = new ModelAndView(); String message =
	  "New member "+member.getCity()+" was successfully created.";
	  
	  memberService.create(member); mav.setViewName("redirect:/index.html");
	  
	  redirectAttributes.addFlashAttribute("message", message); return mav; }
	/*  
	  @RequestMapping(value="/list", method=RequestMethod.GET) public
	  ModelAndView memberListPage() { ModelAndView mav = new
	  ModelAndView("member-list"); List<Member> memberList =
	  memberService.findAll(); mav.addObject("memberList", memberList); return
	  mav; }
	  
	  
	  
	  @RequestMapping(value="/edit/{id}", method=RequestMethod.GET) public
	  ModelAndView editShopPage(@PathVariable Integer id) { ModelAndView mav =
	  new ModelAndView("member-edit"); Member member =
	  memberService.findById(id); mav.addObject("member", member); return mav;
	  }
	  
	  @RequestMapping(value="/edit/{id}", method=RequestMethod.POST) public
	  ModelAndView editShop(@ModelAttribute @Valid Member member, BindingResult
	  result,
	  
	  @PathVariable Integer id, final RedirectAttributes redirectAttributes)
	  throws MemberNotFound {
	  
	  if (result.hasErrors()) return new ModelAndView("member-edit");
	  
	  ModelAndView mav = new ModelAndView("redirect:/index.html"); String
	  message = "Shop was successfully updated.";
	  
	  memberService.update(member);
	  
	  redirectAttributes.addFlashAttribute("message", message); return mav; }
	  
	  @RequestMapping(value="/delete/{id}", method=RequestMethod.GET) public
	  ModelAndView deleteShop(@PathVariable Integer id, final
	  RedirectAttributes redirectAttributes) throws MemberNotFound {
	  
	  ModelAndView mav = new ModelAndView("redirect:/index.html");
	  
	  Member member = memberService.delete(id); String message =
	  "The member "+member.getCity()+" was successfully deleted.";
	  
	  redirectAttributes.addFlashAttribute("message", message); return mav; }
	  */
	 

}
