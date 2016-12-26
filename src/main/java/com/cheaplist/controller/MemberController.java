package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cheaplist.exception.MemberNotFound;
import com.cheaplist.model.Member;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.model.View;
import com.cheaplist.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	/*
	 * @Autowired private ShopValidator memberValidator;
	 * 
	 * /*
	 * 
	 * @InitBinder private void initBinder(WebDataBinder binder) {
	 * binder.setValidator(memberValidator); }
	 */

	/*
	 * @RequestMapping(value="/create", method=RequestMethod.GET) public
	 * ModelAndView newShopPage() { ModelAndView mav = new
	 * ModelAndView("member-new", "member", new Member()); return mav; }
	 */
	@JsonView(View.MemberIdentity.class)
	@RequestMapping(value = "/identity/findall/")
	public List<Member> identityFindAll() {
		ArrayList<Member> memberList = (ArrayList<Member>) memberService.findAll();
		for (Member member : memberList) {
			System.out.println(member.toString());
			System.out.println("Name :" + member.getName() + "  Id : " + member.getEmail());
		}
		return memberList;

	}
	
	@JsonView(View.MemberShop.class)
	@RequestMapping(value = "/shop/findall/")
	public List<Member> shopFindAll() {
		ArrayList<Member> memberList = (ArrayList<Member>) memberService.findAll();
		for (Member member : memberList) {
			System.out.println(member.toString());
			System.out.println("Name :" + member.getName() + "  Id : " + member.getEmail());
		}
		return memberList;

	}

	@JsonView(View.MemberIdentity.class)
	@RequestMapping(value = "/identity/findbyid/{id}", method = RequestMethod.PUT)
	public Member identityFindId(@PathVariable Integer id) {

		Member member;
		if (id == null) {
			member = null;
		} else {
			// System.out.println("Test Seb");
			member = memberService.findById(id.intValue());
			// System.out.println("Test Seb2");
			System.out.println(member.toString());
			System.out.println("Seb : " + member.getAddress().getCity());

		}
		System.out.println("Test Seb4");
		return member;

	}

	@JsonView(View.MemberList.class)
	@RequestMapping(value = "/list/findbyid/{id}")
	public Member listFindid(@PathVariable Integer id) {
		Member member;
		if (id == null) {
			member = null;
		} else {
			member = memberService.findById(id.intValue());
			Set<ShoppingList> Test = member.getShoppingLists();
			for (ShoppingList Shop : Test) {
				System.out.println(Shop.getName());
			}
		}
		System.out.println("Test Seb4");
		return member;

	}

	/** test Google Map **/
	@RequestMapping(value = "/googlemap")
	public String googleTest() {
		RestTemplate restTemplate = new RestTemplate();
		String Seb = new String();
		/*
		 * Seb = restTemplate.getForObject(
		 * "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false",
		 * String.class,"90 rue Baudin, 92300 Levallois Perret");
		 */
		
		  Seb = restTemplate.getForObject(
	 "https://maps.googleapis.com/maps/api/distancematrix/json?origins=50.6138111,3.0423598999999997&destinations=50.73797619999999,3.1360174&language=fr-FR&key=AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA",String.class);
		  System.out.println(Seb);

		return Seb;
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
