package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cheaplist.exception.OpenfactfoodNotFound;
import com.cheaplist.model.Openfactfood;
import com.cheaplist.service.OpenfactfoodService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/off")
public class OpenfactfoodController {

	@Autowired
	private OpenfactfoodService openfactfoodService;

	/** test Google Map **/
	@RequestMapping(value = "/go")
	public String googleTest() {
		RestTemplate restTemplate = new RestTemplate();
		String Seb = new String();
		/*
		 * Seb = restTemplate.getForObject(
		 * "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false",
		 * String.class,"90 rue Baudin, 92300 Levallois Perret");
		 */
		/*
		for (BigInteger a = BigInteger.ONE; 
			     a.compareTo(10000000000000) < 0; 
			     a = a.add(BigInteger.ONE)) {

		        System.out.println(bi);
		    }
		    
		Seb = restTemplate.getForObject("http://fr.openfoodfacts.org/api/v0/produit/{address}.json", String.class,i);
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(Seb));
			
			Openfactfood off = new Openfactfood();

			JsonNode innerNode = rootNode.get("product"); // Get the only
															// element in the
			
			
			String[] Categorie;
			try {
				JsonNode aField = innerNode.get("categories");
				String Categories = mapper.treeToValue(aField, String.class);
				Categorie = Categories.split(",");
				if (Categorie.length > 0)
					off.setCategorie1(Categorie[1]);

				if (Categorie.length > 1)
					off.setCategorie2(Categorie[2]);

				if (Categorie.length > 2)
					off.setCategorie3(Categorie[3]);

				if (Categorie.length > 3)
					off.setCategorie4(Categorie[4]);

				if (Categorie.length > 4)
					off.setCategorie5(Categorie[5]);

				if (Categorie.length > 5)
					off.setCategorie6(Categorie[6]);

				if (Categorie.length > 6)
					off.setCategorie7(Categorie[7]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				JsonNode bField = innerNode.get("brands");
				String brands = mapper.treeToValue(bField, String.class);
				off.setBrands(brands);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				JsonNode cField = innerNode.get("product_name_fr");
				String name = mapper.treeToValue(cField, String.class);
				off.setName(name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				JsonNode dField = innerNode.get("generic_name");
				String generic = mapper.treeToValue(dField, String.class);
				off.setGeneric(generic);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				JsonNode eField = innerNode.get("quantity");
				String quantity = mapper.treeToValue(eField, String.class);
				off.setQuantity(quantity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Seboou");
			off.setTruecategory("nothing");
		//	off.toString();
			
			if (off.getBrands() != null)
			openfactfoodService.create(off);
			System.out.print("Yes!!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Seboou");
			e.printStackTrace();
		}
*/
		return Seb;
	}

}
