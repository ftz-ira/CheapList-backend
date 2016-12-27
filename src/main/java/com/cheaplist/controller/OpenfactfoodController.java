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
		int chiffre = 1;
		String nombre = "3000000000000";
		
		int checkpoint = 0;

		while (!nombre.equals("4000000000000")) {
			Seb = restTemplate.getForObject("http://fr.openfoodfacts.org/api/v0/produit/{idjson}.json", String.class,
					nombre);
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(new StringReader(Seb));
		
				JsonNode test = rootNode.get("status_verbose");
				String Trouver = mapper.treeToValue(test,String.class);
				if (!Trouver.equals("product not found"))
				{
					System.out.println("CheckSeb "+nombre);
				
				Openfactfood off = new Openfactfood();
				JsonNode innerNode = rootNode.get("product");

				String[] Categorie;
				try {
					JsonNode aField = innerNode.get("categories");
					String Categories = mapper.treeToValue(aField, String.class);
					Categorie = Categories.split(",");
					if (Categorie.length > 0)
						off.setCategorie1(Categorie[0]);

					if (Categorie.length > 1)
						off.setCategorie2(Categorie[1]);

					if (Categorie.length > 2)
						off.setCategorie3(Categorie[2]);

					if (Categorie.length > 3)
						off.setCategorie4(Categorie[3]);

					if (Categorie.length > 4)
						off.setCategorie5(Categorie[4]);

					if (Categorie.length > 5)
						off.setCategorie6(Categorie[5]);

					if (Categorie.length > 6)
						off.setCategorie7(Categorie[6]);
				} catch (Exception e) { // TODO Auto-generated catch block
					
				}

				try {
					JsonNode bField = innerNode.get("brands");
					String brands = mapper.treeToValue(bField, String.class);
					off.setBrands(brands.split(",")[0]);
				} catch (Exception e) { // TODO Auto-generated catch block
				
				}

				try {
					JsonNode bField = innerNode.get("image_front_small_url");
					String url = mapper.treeToValue(bField, String.class);
					off.setUrl((url));
				} catch (Exception e) { // TODO Auto-generated catch block
					
				}

				try {
					JsonNode bField = innerNode.get("code");
					BigInteger code = mapper.treeToValue(bField, BigInteger.class);
					off.setEan(code);
				} catch (Exception e) { // TODO Auto-generated catch block
					
				}

				try {
					JsonNode cField = innerNode.get("product_name_fr");
					String name = mapper.treeToValue(cField, String.class);
					off.setName(name);
				} catch (Exception e) { // TODO Auto-generated catch block
				
				}

				try {
					JsonNode dField = innerNode.get("generic_name");
					String generic = mapper.treeToValue(dField, String.class);
					off.setGeneric(generic);
				} catch (Exception e) { // TODO Auto-generated catch block
					
				}

				try {
					JsonNode eField = innerNode.get("quantity");
					String quantity = mapper.treeToValue(eField, String.class);
					off.setQuantity(quantity.split(" ")[1]);
					off.setVolume(Integer.parseInt((quantity.split(" ")[0])));
					
				} catch (Exception e) { // TODO Auto-generated catch block
					
				}
				off.setTruecategory("nothing"); // off.toString();
				System.out.print("Donnees Inseree");
				openfactfoodService.create(off);
				}
					
				
			} catch (IOException e) { // TODO Auto-generated catch block
			
				e.printStackTrace();
			}
			

			int retenue = 1;
			while (chiffre < 14 && retenue == 1) {
				char[] mynombre = nombre.toCharArray();
				// System.out.println("Test mynombre : "+mynombre.length+"
				// "+nombre.length());
				int Tempo = ((int) (mynombre[mynombre.length - chiffre]) + 1);
				// System.out.println("Tempo : "+Tempo+" :"+ (char)Tempo);
				if (Tempo > 57) {
					retenue = 1;
					Tempo = 48;
				} else {
					retenue = 0;
				}
				mynombre[mynombre.length - chiffre] = (char) Tempo;
				nombre = nombre.valueOf(mynombre);
				chiffre++;
			}
			chiffre = 1;
			++checkpoint;
			if(checkpoint>1000)
			{
				System.out.println("Check : +" + nombre);
				checkpoint = 0;
			}
		}



	return Seb;
}

}
