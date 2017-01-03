package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.model.Address;
import com.cheaplist.model.Shop;
import com.cheaplist.service.ShopService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/google")


public class GoogleController {
	
	@Autowired
	private ShopService shopService;

	/** test Google Map **/
	@RequestMapping(value = "/shop")
	public String googleTest() {
		RestTemplate restTemplate = new RestTemplate();
		String Seb = new String();		
		
		List <String> Emblem = new ArrayList <String>();
		Emblem.add("Auchan");
		Emblem.add("Carrefour");
		Emblem.add("Cora");
		Emblem.add("Leclerc");
		Emblem.add("Lidl");
		Emblem.add("Match");
		Emblem.add("Géant Casino");
		
		for (String Embleme : Emblem)
		{
		  Seb = restTemplate.getForObject(
	 "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=50.6138111,3.0423598999999997&radius=50000&types=grocery_or_supermarket&name="+Embleme+"&key=AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA",String.class);
//		  System.out.println(Seb);
		  
		  // Attention JSON powaaaaaaaa
		  
		  try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(Seb));
			int value=0;
			  for (JsonNode node : rootNode.path("results")) {
				  System.out.println("Numéro: "+value+"  ID :"+node.path("id").asText());
				  System.out.println("NOM :"+node.path("name").asText());
				  String Adresse = node.path("vicinity").asText();

				  
				  String Rue;
				try {
					Rue = (Adresse.split(",")[Adresse.split(",").length-2]);
				} catch (Exception e) { Rue="";
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  String Ville = (Adresse.split(",")[Adresse.split(",").length-1]);
				  System.out.println("Rue : "+Rue+"  Ville : "+ Ville);				  
				  System.out.println("Lat :"+node.path("geometry").path("location").path("lat").asText()+"Long:"+node.path("geometry").path("location").path("lng").asText());
				  value=value+1;
				  System.out.println("\n");
				  
				  Shop Magasin = new Shop();
				  Address Address_Magasin = new Address();
				  Magasin.setName(node.path("name").asText());
				  Magasin.setEmblem(Embleme);
				  Magasin.setIsActive((byte) '1');
				  Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("03-01-2017 11:30:00");
				  Magasin.setCreatedDate(date);
				  Magasin.setIdgoogle(node.path("id").asText());
				  Address_Magasin.setStreetName(Rue);
				  Address_Magasin.setCity(Ville);
				  Address_Magasin.setLag(node.path("geometry").path("location").path("lat").asDouble());
				  Address_Magasin.setLng(node.path("geometry").path("location").path("lng").asDouble());
				//  Address_Magasin.setZipCode(0);				  
				  Magasin.setAddress(Address_Magasin);
				 shopService.create(Magasin);
				  
			  }
		  
		  
		  
		  
		  
		  
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return Seb;
	

	}
	
}
