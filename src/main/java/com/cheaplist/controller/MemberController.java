package com.cheaplist.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.exception.MemberNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.Member;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.model.View;
import com.cheaplist.service.MemberService;
import com.cheaplist.service.ShoppingListService;
import com.cheaplist.validator.MemberValidator;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * CREATE  --> AUTORISER
 * READ    --> DONE
 * PATCH   --> 
 * DELETE  --> SAVE ET CREATE
 * 
 */	


//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/members")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ShoppingListService shoppingListService;

	
	 @Autowired private MemberValidator memberValidator;
			
	 @InitBinder private void initBinder(WebDataBinder binder) {
	  binder.setValidator(memberValidator); }
	 
	 /*****  CREATE A NEW MEMBER     ******/
	 @RequestMapping(value="/",method=RequestMethod.PUT,consumes="application/json",produces="application/json")
	 List<ObjectError> createNewShop(@RequestBody Member member,BindingResult result) 			
	 { 
		 	memberValidator.validate(member,result);		 	
		 	if (result.hasErrors()) return result.getAllErrors();	
		 	System.out.println(member.toString());
		 	member =memberService.create(member);	
		 	
		 	/**** By default, a new member has 9 lists.
		 	 */
		 	for (int i=1; i <10; i++)
		 	{
		 		/** Create Empty ShoppingList Method **/
		 		ShoppingList shoppingList = new ShoppingList();
		 		 shoppingList.setName(member.getName()+" List"+i);
		 		 shoppingList.setMember(member);
		 		 shoppingList.setIsActif(true);
		 		 shoppingList.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		 		 shoppingList.setIsFavor(null);
		 		 shoppingList.setIsClose(false);
		 		 shoppingList.setIsDone(false);
		 		shoppingListService.create(shoppingList);
		 	}
		 	return null;
	 }
	 
	 
	 /*****  READ ALL METHODE  : ALL MEMBER  ******/
	@JsonView(View.MemberIdentity.class)
	@RequestMapping(method=RequestMethod.GET)
	public List<Member> identityFindAll() {
		ArrayList<Member> memberList = (ArrayList<Member>) memberService.findAll();
		return memberList;

	}
	
	 /*****  READ ONE METHODE : ONE MEMBER   ******/
	@JsonView(View.MemberIdentity.class)
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public Member identityFindId(@PathVariable Integer id) {

		Member member;
		member = memberService.findById(id.intValue());
		return member;

	}

	
	/*****  READ ONE MEMBER : ALL LIST BY MEMBER   ******/
	@JsonView(View.MemberList.class)
	@RequestMapping(value = "/{id}/lists")
	public Member listFindid(@PathVariable Integer id) {
		Member member;
		member = memberService.findById(id.intValue());
		return member;
	}

	/***** DELETE A MEMBER 
	 * @throws MemberNotFound *******/
	
	 @RequestMapping(value="/{idMember}",method=RequestMethod.DELETE)
	 public ResponseEntity<String> RemoveOneMember(@PathVariable Integer idMember) throws MemberNotFound 			
	 { 
		 Member member = memberService.findById(idMember);
		 member.setIsActive(false);
		 memberService.update(member);
		 return new ResponseEntity<String>("ELEMENT DELETED", HttpStatus.OK);
	 }
	 
	 
	 
	 /**** PATCH A MEMBER  **********/
	 @JsonView(View.MemberIdentity.class)
	 @RequestMapping(value = "/{idMember}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	 public Member PatchMember(@PathVariable Integer idMember, @RequestBody Member member) throws MemberNotFound {
			member = memberService.patch(idMember,member);
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
