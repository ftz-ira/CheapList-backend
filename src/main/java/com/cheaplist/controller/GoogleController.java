package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.model.Shop;
import com.cheaplist.model.View;
import com.cheaplist.service.ShopService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/google")

public class GoogleController {

	@Autowired
	private ShopService shopService;

	/********* GET LIST SHOPS ACCORDING TO USER POSITION GPS ***************/
	@JsonView(View.GoogleShop.class)
	@RequestMapping(value = "/shops/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ArrayList<Shop> googleTest(@RequestBody String coordinate) {

		/*
		 * Algorithme retenu: Le front envoie les coordonnées GPS (pour le
		 * moment le radius sera par défaut) -- A travailler sur la bdd
		 */
		System.out.println(coordinate);

		/*** Mettre un validator coordinate (plus tard) ****/

		/** SI le flag error n'est pas levé **/
		
		String answerGoogle= "";
		ArrayList<Shop> shops = new ArrayList <Shop>() ;
		

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(coordinate));
			String lat = rootNode.path("lat").asText();
			String lng = rootNode.path("lng").asText();
			String radius= "2000"; // Par défaut
			String emblem="Auchan|Carrefour|Cora|Leclerc|Lidl|Match|Géant Casino";
			
			RestTemplate restTemplate = new RestTemplate();
			answerGoogle = restTemplate.getForObject(
					  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lng+","+lat+"&radius="+radius+"&types=grocery_or_supermarket&name="+emblem+"&key=AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA"
					  ,String.class); // System.out.println(Seb);
			String Seb="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lng+","+lat+"&radius="+radius+"&types=grocery_or_supermarket&name="+emblem+"&key=AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA";
			System.out.println(Seb);
			
			
			JsonNode googleNode = mapper.readTree(new StringReader(answerGoogle));
			for (JsonNode node : googleNode.path("results")) {
				String idgoogle = node.path("id").asText();
				System.out.println("Test Seb : "+idgoogle);
				Shop shop = shopService.findShopByIdgoogle(idgoogle);
				
				if (shop != null)
				{
					shop.getAddress().setMembers(null);
					shop.setMembers(null);
					shops.add(shop);
				}
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return shops;

	}

}
