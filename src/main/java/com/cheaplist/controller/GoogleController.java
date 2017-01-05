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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/google")

public class GoogleController {

	@Autowired
	private ShopService shopService;

	/********* GET LIST SHOPS ACCORDING TO USER POSITION GPS ***************/
	@JsonView(View.GoogleShop.class)
	@RequestMapping(value = "/shops/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String googleTest(@RequestBody String coordinate) {

		/*
		 * Algorithme retenu: Le front envoie les coordonnées GPS (pour le
		 * moment le radius sera par défaut) -- A travailler sur la bdd
		 */
		System.out.println(coordinate);

		/*** Mettre un validator coordinate (plus tard) ****/

		/** SI le flag error n'est pas levé **/

		String answerGoogle = "";
		ArrayList<Shop> shops = new ArrayList<Shop>();
		ObjectMapper mapper;
		ArrayNode arrayNode;
		String answerJson = null;

		try {
			mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(coordinate));
			String lat = rootNode.path("lat").asText();
			String lng = rootNode.path("lng").asText();
			String radius = "2000"; // Par défaut
			String emblem = "Auchan|Carrefour|Cora|Leclerc|Lidl|Match|Géant Casino";
			String key = "AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA";

			RestTemplate restTemplate = new RestTemplate();
			answerGoogle = restTemplate.getForObject(
					"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lng + "," + lat
							+ "&radius=" + radius + "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key,
					String.class); // System.out.println(Seb);

			JsonNode googleNode = mapper.readTree(new StringReader(answerGoogle));
			mapper = new ObjectMapper();
			arrayNode = mapper.createArrayNode();
			for (JsonNode node : googleNode.path("results")) {

				String idgoogle = node.path("id").asText();
				System.out.println("Test Seb : " + idgoogle);
				Shop shop = shopService.findShopByIdgoogle(idgoogle);

				if (shop != null) {
					/*** A vérifier si on peut le faire sans ***/
					shop.getAddress().setMembers(null);
					shop.setMembers(null);
					String latShop = String.valueOf(shop.getAddress().getLag());
					String lntShop = String.valueOf(shop.getAddress().getLng());				
					
					  restTemplate = new RestTemplate(); 
					  answerGoogle =
							  restTemplate.getForObject(
							  "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
							  +lng+","+lat+"&destinations="+latShop+","+lntShop+"&language=fr-FR&key="+
							  key ,String.class); System.out.println(answerGoogle);
					
							  System.out.println(answerGoogle);
					/**** JSON MANUELLE car il est customise *****/
				JsonNode googleNode2 = mapper.readTree(new StringReader(answerGoogle));	
				String idgoogle2 = googleNode2.path("rows").path("elements").asText();
							  
							  
							  
					ObjectNode objectNode1 = mapper.createObjectNode();
					objectNode1.put("name",shop.getName());
					objectNode1.put("distance","50");
					arrayNode.add(objectNode1);
					
				}

		

			}
			 answerJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
			
		
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return answerJson;
	}

	// https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&language=fr-FR&key=YOUR_API_KEY

}
