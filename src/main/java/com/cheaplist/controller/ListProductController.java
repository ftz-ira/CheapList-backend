package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.Shop;
import com.cheaplist.model.View;
import com.cheaplist.service.ListProductService;
import com.cheaplist.service.ShopService;
import com.cheaplist.utility.MathGPS;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/lists")
public class ListProductController {

	@Autowired
	private ListProductService listProductService;

	@Autowired
	private ShopService shopService;

	/*
	 * @Autowired private ListProductValidator listProductValidator;
	 */

	/***** GET ALL PRODUCT ID BY LIST ID ******/

	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<ListProduct>> ListProductAll(@PathVariable Integer id) throws ExceptionMessage {
		List<ListProduct> listProductList = listProductService.findProductsByList(id.intValue());
		return new ResponseEntity<List<ListProduct>>(listProductList, HttpStatus.OK);

	}

	/**** GET ONE PRODUCT BY LIST ID (ONE ELEMENT) *****/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.GET)
	public ResponseEntity<ListProduct> ListProduct(@PathVariable Integer idList, @PathVariable Integer idElement)
			throws ExceptionMessage {
		ListProduct listProduct = listProductService.findProductByList(idList.intValue(), idElement.intValue());
		return new ResponseEntity<ListProduct>(listProduct, HttpStatus.OK);
	}

	/*** PATCH ONE ELEMENT FROM ONE LIST ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ListProduct> PatchListProductAll(@PathVariable Integer idList,
			@PathVariable Integer idElement, @RequestBody ListProduct listProduct) throws ExceptionMessage {
		listProduct = listProductService.patch(idList, idElement, listProduct);
		return new ResponseEntity<ListProduct>(listProduct, HttpStatus.OK);

	}

	/**** PATCH FRANTZ : TRIPLE ACTIONS *****/
	/** CREATE ELEMENT LIST IF ELEMENT NOT EXIST **/
	/** UPDATE QUANTITY IF ELEMENT EXIST **/
	/**
	 * DELETE ELEMENT IF QUANTITY IS ZERO
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 **/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/frantz", method = RequestMethod.PATCH, consumes = "application/json")
	public ResponseEntity<List<ListProduct>> PatchQuantity(@PathVariable Integer idList, @RequestBody String jsondata)
			throws ExceptionMessage, JsonProcessingException, IOException {
		/*** JSON DEMAPPING ***/
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(new StringReader(jsondata));
		int idProduct = rootNode.path("idProduct").asInt();
		int productQuantity = rootNode.path("productQuantity").asInt();

		/****
		 * ETAPE ONE : ON cherche si il existe un élement.. sinon on en crée
		 * un
		 ***/
		ListProduct listProduct = listProductService.findElementByListBtProduct(idList, idProduct);

		// Introuvable donc on en crée un autre élement dans la liste
		if (listProduct == null) {
			if (productQuantity < 1)
				productQuantity = 1;
			listProductService.createOneElement(idList, idProduct, productQuantity);

		} else {
			// Si l'element est trouvé, on va l'update

			if (productQuantity > 0) {
				listProduct.setProductQuantity(productQuantity);
				listProductService.patch(idList, listProduct.getId(), listProduct);
			} else {
				listProductService.delete(listProduct.getId());
			}
		}
		return new ResponseEntity<List<ListProduct>>(listProductService.findProductsByList(idList), HttpStatus.OK);
	}

	/*** ADD ONE ELEMENT FROM ONE LIST ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ListProduct> AddOneElement(@PathVariable Integer idList, @RequestBody String addElement) {

		ObjectMapper mapper = new ObjectMapper();
		ListProduct listProduct = null;
		JsonNode rootNode;

		try {
			rootNode = mapper.readTree(new StringReader(addElement));
			int productQuantity = rootNode.path("productQuantity").asInt();
			int idProduct = rootNode.path("idProduct").asInt();
			// Si le JSON est incorrect
			if (productQuantity < 1 || idProduct < 1)
				return null;
			listProduct = listProductService.createOneElement(idList, idProduct, productQuantity);
			System.out.println(listProduct.getId());
		} catch (IOException | ExceptionMessage e) {
			e.printStackTrace();
			return null;
		}

		return new ResponseEntity<ListProduct>(listProduct, HttpStatus.OK);
	}

	/***
	 * REMOVE ONE ELEMENT FROM ONE LIST
	 * 
	 */
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.DELETE)
	public ResponseEntity<String> RemoveOneElement(@PathVariable Integer idList, @PathVariable Integer idElement)
			throws ExceptionMessage {

		ListProduct listProduct = listProductService.findProductByList(idList.intValue(), idElement.intValue());
		listProductService.delete(listProduct.getId());
		return new ResponseEntity<String>("ELEMENT DELETED", HttpStatus.OK);
	}

	/*
	 * 
	 * CALCULE LE DEVIS D'UNE LISTE
	 * 
	 * 
	 * C'est Parti
	 */

	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}", method = RequestMethod.POST)
	public String ListAllPrices(@PathVariable Integer idList, @RequestBody String coordinate) {
		System.out.println(coordinate);
		String answerGoogle = "";
		ObjectMapper mapper;
		ArrayNode arrayNode;
		String answerJson = null;

		/*** On récupère les ID des magasins autour de l'utilisateur ****/
		try {
			mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(coordinate));
			String lat = rootNode.path("lat").asText();
			String lng = rootNode.path("lng").asText();
			/** Mettre EXCEPTION ****/

			String radius = "3500"; // Par defaut
			String emblem = "Auchan|Carrefour|Cora|Leclerc|Lidl|Match|G�ant Casino";
			String key = "AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA";

			// On r�cup�re la liste des magasins à partir du GPS du client
			RestTemplate restTemplate = new RestTemplate();
			answerGoogle = restTemplate.getForObject(
					"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng
							+ "&radius=" + radius + "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key,
					String.class);

			// System.out.println("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
			// + lat + ","
			// + lng + "&radius=" + radius +
			// "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key);

			// On trie le JSON donnée par GOOGLE
			JsonNode googleNode = mapper.readTree(new StringReader(answerGoogle));
			mapper = new ObjectMapper();
			arrayNode = mapper.createArrayNode();
			long numberElement = listProductService.countElement(idList);
			for (JsonNode node : googleNode.path("results")) {

				// Pour chaque magasin donné par GOOGLE, on verifie qu'il est
				// bien dans notre BD // Ils ont un attribut commun : idGoogle
				String idgoogle = node.path("id").asText();
				Shop shop = shopService.findShopByIdgoogle(idgoogle);

				// Pour chaque magasin trouvé
				if (shop != null) {
					double latShop = shop.getAddress().getLag();
					double lntShop = shop.getAddress().getLng();

					double resultat = MathGPS.distance(Double.parseDouble(lat), Double.parseDouble(lng), latShop,
							lntShop, "K");
					ObjectNode objectNode1 = mapper.createObjectNode();
					objectNode1.put("id", shop.getId());
					objectNode1.put("name", shop.getName());
					objectNode1.put("distance", resultat);
					arrayNode.add(objectNode1);

					double devis = listProductService.findPrice(idList, shop.getId());
					long missing = listProductService.findMissing(idList, shop.getId());
					objectNode1.put("prix total", String.valueOf(devis));
					objectNode1.put("element manquant", String.valueOf(numberElement - missing));
				}
			}

			answerJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);

		} catch (IOException e) {

		}

		return answerJson;

	}
}
