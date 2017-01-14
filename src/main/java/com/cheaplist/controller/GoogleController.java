package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.model.Shop;
import com.cheaplist.model.View;
import com.cheaplist.service.ShopService;
import com.cheaplist.utility.MathGPS;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/shops/distances")

public class GoogleController {

	@Autowired
	private ShopService shopService;

	/********* GET LIST SHOPS ACCORDING TO USER POSITION GPS ***************/
	@JsonView(View.GoogleShop.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String googleTest(@RequestBody String coordinate) {

		/*** Mettre un validator coordinate (plus tard) ****/

		/** SI le flag error n'est pas leve **/

		String answerGoogle = "";
		ObjectMapper mapper;
		ArrayNode arrayNode;
		String answerJson = null;
	

		try {
			mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(coordinate));
			String lat = rootNode.path("lat").asText();
			String lng = rootNode.path("lng").asText();
			String radius = "15000"; // Par defaut
			String emblem = "Auchan|Carrefour|Cora|Leclerc|Lidl|Match|G�ant Casino";
			String key = "AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA";

			// On récupère la liste des magasins à partir du GPS du client
			RestTemplate restTemplate = new RestTemplate();
			answerGoogle = restTemplate.getForObject(
					"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng
							+ "&radius=" + radius + "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key,
					String.class);

			System.out.println("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng
					+ "&radius=" + radius + "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key);
			// On trie le JSON donnée par GOOGLE
			JsonNode googleNode = mapper.readTree(new StringReader(answerGoogle));
			mapper = new ObjectMapper();
			arrayNode = mapper.createArrayNode();
			
			int i=0;
			for (JsonNode node : googleNode.path("results")) {
			
				// Pour chaque magasin donné par GOOGLE, on v�rifie qu'il est bien dans notre BD
				// Ils ont un attribut commun : idGoogle
				String idgoogle = node.path("id").asText();
				System.out.println("Seb: "+idgoogle);
				Shop shop = shopService.findShopByIdgoogle(idgoogle);

				if (shop != null) {
					
					i++;
					System.out.println("Test i :"+i);
					// ID Validé, on prend les coordonnées GPS du magasin et on calcule la distance entre l'user et les magasins. 

					double latShop = shop.getAddress().getLag();
					double lntShop = shop.getAddress().getLng();

					double resultat = MathGPS.distance(Double.parseDouble(lat), Double.parseDouble(lng), latShop,
							lntShop, "K");
					ObjectNode objectNode1 = mapper.createObjectNode();
					objectNode1.put("id", shop.getId());
					objectNode1.put("name", shop.getName());
					objectNode1.put("distance", resultat);
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

	// https://maps.googleapis.com/maps/api/distancematrix/json?origins=50.6138111,3.0423598999999997&destinations=50.6222098,3.060641599999999&key=AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA

}
