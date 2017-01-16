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

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.Shop;
import com.cheaplist.model.View;
import com.cheaplist.service.ListProductService;
import com.cheaplist.service.ShopService;
import com.cheaplist.utility.MathGPS;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/lists")
public class ListProductController {

	/*
	 * Attention BUG: Contr�ler l'ajout d'un element (v�rifier que le produit
	 * n'est pas dans la liste des �lements au pr�able)
	 * 
	 * 
	 */

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
	public List<ListProduct> ListProductAll(@PathVariable Integer id) {
		List<ListProduct> listProductList = listProductService.findProductsByList(id.intValue());
		return listProductList;

	}

	/**** GET ONE PRODUCT BY LIST ID (ONE ELEMENT) *****/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.GET)
	public ListProduct ListProduct(@PathVariable Integer idList, @PathVariable Integer idElement) {
		ListProduct listProduct = listProductService.findProductByList(idList.intValue(), idElement.intValue());
		return listProduct;

	}

	/*** PATCH ONE ELEMENT FROM ONE LIST ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	public ListProduct PatchListProductAll(@PathVariable Integer idList, @PathVariable Integer idElement,
			@RequestBody ListProduct listProduct) throws ListProductNotFound {
		System.out.println("Test Sebs");
		listProduct = listProductService.patch(idList, idElement, listProduct);
		return listProduct;

	}

	/*** ADD ONE ELEMENT FROM ONE LIST ***/
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ListProduct AddOneElement(@PathVariable Integer idList, @RequestBody String addElement) {

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
		} catch (IOException | ListProductNotFound e) {
			e.printStackTrace();
			return null;
		}

		return listProduct;
	}

	/***
	 * REMOVE ONE ELEMENT FROM ONE LIST
	 * 
	 */
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.DELETE)
	public ResponseEntity<String> RemoveOneElement(@PathVariable Integer idList, @PathVariable Integer idElement)
			throws ListProductNotFound {

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
			String radius = "3500"; // Par defaut
			String emblem = "Auchan|Carrefour|Cora|Leclerc|Lidl|Match|G�ant Casino";
			String key = "AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA";

			// On récupère la liste des magasins à partir du GPS du client
			RestTemplate restTemplate = new RestTemplate();
			answerGoogle = restTemplate.getForObject(
					"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng
							+ "&radius=" + radius + "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key,
					String.class);

			System.out.println("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + ","
					+ lng + "&radius=" + radius + "&types=grocery_or_supermarket&name=" + emblem + "&key=" + key);
			// On trie le JSON donnée par GOOGLE
			JsonNode googleNode = mapper.readTree(new StringReader(answerGoogle));
			mapper = new ObjectMapper();
			arrayNode = mapper.createArrayNode();
			long numberElement = listProductService.countElement(idList);
			for (JsonNode node : googleNode.path("results")) {

				// Pour chaque magasin donné par GOOGLE, on verifie qu'il est
				// bien dans notre BD // Ils ont un attribut commun : idGoogle
				String idgoogle = node.path("id").asText();
				System.out.println("Seb: " + idgoogle);
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
					
					double devis = listProductService.findPrice(idList,shop.getId());
					long missing = listProductService.findMissing(idList,shop.getId());
					objectNode1.put("devis",String.valueOf(devis));
					objectNode1.put("missing",String.valueOf(numberElement- missing));
					System.out.println("Devis Test :"+devis+ "   Missing : "+missing);
					
							
				}
			}

			answerJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);

		} catch (IOException e) {

		}

		return answerJson;

	}
}
