package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/google")
public class GoogleController {

	/** test Google Map **/
	@RequestMapping(value = "/shop")
	public String googleTest() {
		RestTemplate restTemplate = new RestTemplate();
		String Seb = new String();
		/*
		 * Seb = restTemplate.getForObject(
		 * "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false",
		 * String.class,"90 rue Baudin, 92300 Levallois Perret");
		 */
		
		  Seb = restTemplate.getForObject(
	 "http://fr.openfoodfacts.org/api/v0/produit/3596710439799.json",String.class);
		 try {
			 ObjectMapper mapper = new ObjectMapper();
			 JsonNode rootNode = mapper.readTree(new StringReader(Seb));
			
			JsonNode innerNode = rootNode.get("product"); // Get the only element in the root node
		    // get an element in that node
		    JsonNode aField = innerNode.get("categories_prev_tags");
		    ArrayList<String> Categorie = mapper.treeToValue(aField,ArrayList.class);
		    System.out.println("Test Tableau"+Categorie.size());
		    for (String test : Categorie)
		    {
		    	System.out.println("Categorie"+ test);
		    }

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  System.out.println(Seb);

		return Seb;
	}

	
	
	/*
	 * @RequestMapping(value="/create", method=RequestMethod.POST) public
	 * ModelAndView createNewShop(@ModelAttribute @Valid Member member,
	 * BindingResult result, final RedirectAttributes redirectAttributes) {
	 * 
	 * if (result.hasErrors()) return new ModelAndView("member-new");
	 * 
	 * ModelAndView mav = new ModelAndView(); String message =
	 * "New member "+member.getCity()+" was successfully created.";
	 * 
	 * memberService.create(member); mav.setViewName("redirect:/index.html");
	 * 
	 * redirectAttributes.addFlashAttribute("message", message); return mav; }
	 * 
	 * @RequestMapping(value="/list", method=RequestMethod.GET) public
	 * ModelAndView memberListPage() { ModelAndView mav = new
	 * ModelAndView("member-list"); List<Member> memberList =
	 * memberService.findAll(); mav.addObject("memberList", memberList); return
	 * mav; }
	 * 
	 * 
	 * 
	 * @RequestMapping(value="/edit/{id}", method=RequestMethod.GET) public
	 * ModelAndView editShopPage(@PathVariable Integer id) { ModelAndView mav =
	 * new ModelAndView("member-edit"); Member member =
	 * memberService.findById(id); mav.addObject("member", member); return mav;
	 * }
	 * 
	 * @RequestMapping(value="/edit/{id}", method=RequestMethod.POST) public
	 * ModelAndView editShop(@ModelAttribute @Valid Member member, BindingResult
	 * result,
	 * 
	 * @PathVariable Integer id, final RedirectAttributes redirectAttributes)
	 * throws MemberNotFound {
	 * 
	 * if (result.hasErrors()) return new ModelAndView("member-edit");
	 * 
	 * ModelAndView mav = new ModelAndView("redirect:/index.html"); String
	 * message = "Shop was successfully updated.";
	 * 
	 * memberService.update(member);
	 * 
	 * redirectAttributes.addFlashAttribute("message", message); return mav; }
	 * 
	 * @RequestMapping(value="/delete/{id}", method=RequestMethod.GET) public
	 * ModelAndView deleteShop(@PathVariable Integer id, final
	 * RedirectAttributes redirectAttributes) throws MemberNotFound {
	 * 
	 * ModelAndView mav = new ModelAndView("redirect:/index.html");
	 * 
	 * Member member = memberService.delete(id); String message =
	 * "The member "+member.getCity()+" was successfully deleted.";
	 * 
	 * redirectAttributes.addFlashAttribute("message", message); return mav; }
	 * 
	 */

}
