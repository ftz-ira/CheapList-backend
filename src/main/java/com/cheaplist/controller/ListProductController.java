package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.exception.ErrorResponse;
import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.Product;
import com.cheaplist.model.Shop;
import com.cheaplist.model.ShopProduct;
import com.cheaplist.model.View;
import com.cheaplist.service.ListProductService;
import com.cheaplist.service.ShopProductService;
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
	private ShopProductService shopProductService;

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
		if (listProductList.isEmpty()) {
			throw new ExceptionMessage("ERROR ID LIST NOT FOUND 001");
		}
		return new ResponseEntity<List<ListProduct>>(listProductList, HttpStatus.OK);

	}

	/**** GET ONE PRODUCT BY LIST ID (ONE ELEMENT) *****/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.GET)
	public ResponseEntity<ListProduct> ListProduct(@PathVariable Integer idList, @PathVariable Integer idElement)
			throws ExceptionMessage {
		ListProduct listProduct = listProductService.findProductByList(idList.intValue(), idElement.intValue());
		if (listProduct == null) {
			throw new ExceptionMessage("ERROR ID LIST OR ID PRODUCT NOT FOUND (code 002)");
		}
		return new ResponseEntity<ListProduct>(listProduct, HttpStatus.OK);
	}

	/*** PATCH ONE ELEMENT FROM ONE LIST ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ListProduct> PatchListProductAll(@PathVariable Integer idList,
			@PathVariable Integer idElement, @RequestBody ListProduct listProduct) throws ExceptionMessage {
		try {
			listProduct = listProductService.patch(idList, idElement, listProduct);
		} catch (ExceptionMessage e) {
			throw new ExceptionMessage("ERROR ListProduct Patch ");
		}
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
			if (productQuantity > 0)
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

	/***
	 * ADD ONE ELEMENT FROM ONE LIST
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<ListProduct>> AddOneElement(@PathVariable Integer idList, @RequestBody String addElement)
			throws ExceptionMessage, JsonProcessingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ListProduct listProduct = null;
		List<ListProduct> listProductList = null;
		JsonNode rootNode;

		rootNode = mapper.readTree(new StringReader(addElement));
		int productQuantity = rootNode.path("productQuantity").asInt();
		int idProduct = rootNode.path("idProduct").asInt();
		// Si le JSON est incorrect
		if (productQuantity < 1 || idProduct < 1)
			throw new ExceptionMessage("ERROR ProductQuantity or IdProduct ERROR");

		if (listProductService.findElementByListBtProduct(idList, idProduct) != null)
			throw new ExceptionMessage("ERROR ELEMENT ALREADY LIST");

		listProduct = listProductService.createOneElement(idList, idProduct, productQuantity);
		listProductList = listProductService.findProductsByList(idList);

		System.out.println(listProduct.getId());
		return new ResponseEntity<List<ListProduct>>(listProductList, HttpStatus.OK);

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
	 * C'est Part
	 */

	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}", method = RequestMethod.POST)
	public ResponseEntity<String> ListAllPrices(@PathVariable Integer idList, @RequestBody String coordinate)
			throws ExceptionMessage {
		System.out.println(coordinate);
		String answerGoogle = "";
		ObjectMapper mapper;
		ArrayNode arrayNode;
		String answerJson = null;

		/*** On récupere les ID des magasins autour de l'utilisateur ****/
		try {
			mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(coordinate));
			String lat = rootNode.path("lat").asText();
			String lng = rootNode.path("lng").asText();
			/** Mettre EXCEPTION ****/
			if (lat.isEmpty() || lng.isEmpty())
				throw new ExceptionMessage("ERROR JSON PACKAGE, MISSING LAT OR LNG DATA");

			String radius = "3500"; // Par defaut
			String emblem = "Auchan|Carrefour|Cora|Leclerc|Lidl|Match|Casino";
			String key = "AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA";

			// On recupere la liste des magasins à partir du GPS du client
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

			if (googleNode.path("status").asText().compareTo("ZERO_RESULTS") == 0) {
				throw new ExceptionMessage("ERROR GOOGLEMAP REQUEST : ZERO RESULTS");
			}

			long numberElement = listProductService.countElement(idList);
			for (JsonNode node : googleNode.path("results")) {

				// Pour chaque magasin donné par GOOGLE, on verifie qu'il est
				// bien dans notre BD // Ils ont un attribut commun : idGoogle
				String idgoogle = node.path("id").asText();
				Shop shop = shopService.findShopByIdgoogle(idgoogle);

				// Pour chaque magasin trouve
				if (shop != null) {
					double latShop = shop.getAddress().getLag();
					double lntShop = shop.getAddress().getLng();

					double resultat = MathGPS.distance(Double.parseDouble(lat), Double.parseDouble(lng), latShop,
							lntShop, "K");

					double devis = listProductService.findPrice(idList, shop.getId());

					// Si le total du devis est �gal � 0, alors on ne retient
					// pas la solution pour Google Estimate
					if (devis != 0) {

						long missing = listProductService.findMissing(idList, shop.getId());
						ObjectNode objectNode1 = mapper.createObjectNode();
						objectNode1.put("idshop", shop.getId());
						objectNode1.put("name", shop.getName());
						objectNode1.put("adresse",
								shop.getAddress().getStreetName() + "," + shop.getAddress().getCity());
						objectNode1.put("distance", resultat);
						objectNode1.put("totalprice", String.valueOf(devis));
						objectNode1.put("missingelement", String.valueOf(numberElement - missing));
						arrayNode.add(objectNode1);
					}

				}
			}

			answerJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);

		} catch (IOException e) {

		}

		return new ResponseEntity<String>(answerJson, HttpStatus.OK);

	}

	/***
	 * 
	 * SHOPTIME TEMPLATE
	 * 
	 * @param ex
	 * @return
	 */
	@JsonView(View.ShopTime.class)
	@RequestMapping(value = "/{idList}/shoptime/{idShop}", method = RequestMethod.GET)
	public ResponseEntity<List<ListProduct>> ListAllProductWithPrices(@PathVariable Integer idList,
			@PathVariable Integer idShop) {
		
		
		List<ListProduct> listProductList = listProductService.findProductsByList(idList);
		// Pour chaque �lement de la liste
		for (ListProduct listProduct : listProductList) {
			// On recupere le produit
			Product product = listProduct.getProduct();
			product.cleanShopProducts();
			ShopProduct shopProduct = shopProductService.findPriceByProductShop(product.getId(), idShop);
			if (shopProduct != null) {
			//	System.out.println("Test:"+product.getId());
				listProduct.getProduct().addShopProduct(shopProduct);
			}

		}
		return new ResponseEntity<List<ListProduct>> (listProductList, HttpStatus.OK);

	}

	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}
}
