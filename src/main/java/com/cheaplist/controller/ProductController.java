package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

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
import com.cheaplist.model.Product;
import com.cheaplist.model.Shop;
import com.cheaplist.model.View;
import com.cheaplist.service.CategoryService;
import com.cheaplist.service.ProductService;
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
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	/****
	 * READ ONE PRODUCT
	 * 
	 * @throws ExceptionMessage
	 *****/
	@JsonView(View.Product.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> productOne(@PathVariable Integer id) throws ExceptionMessage {
		Product product = productService.findById(id.intValue());
		if (product == null)
			throw new ExceptionMessage("ID PRODUCT NOT FIND");
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}

	/**** ADD PRODUCT ****/
	/**** BAR CODE ****/

	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@RequestBody String ProductJson)
			throws ExceptionMessage, JsonProcessingException, IOException {
		System.out.println(ProductJson);

		/***
		 * On verifie qu'on a bien les informations necessaires pour l'ajout
		 * dans la BDD
		 ****/
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(new StringReader(ProductJson));
		String idEan = rootNode.path("idEan").asText();
		Integer idCategory = rootNode.path("idCategory").asInt();

		// System.out.println(idCategory);
		/*** EAN VALIDATION *****/
		if (idEan.isEmpty())
			throw new ExceptionMessage("ERROR EAN MISSING");
		if (new BigInteger(idEan).compareTo(new BigInteger("999999999999")) == -1
				|| (new BigInteger(idEan).compareTo(new BigInteger("9999999999999")) == 1))
			throw new ExceptionMessage("ERROR EAN-13 ERROR");

		/*** idCategory VALIDATION ****/
		if (idCategory < 1 || idCategory > 37)
			throw new ExceptionMessage("ERROR CATEGORY JSON");

		/*** On verifie que le produit n'existe pas deja dans notre BDD ***/
		Product product = productService.findByEan(new BigInteger(idEan));
		if (product != null)
			throw new ExceptionMessage("PRODUCT EXISTS IN BDD");

		/*** On verifie qu'il existe bien dans OpenFactFood *****/
		RestTemplate restTemplate = new RestTemplate();
		String answerOpenFactFood = restTemplate
				.getForObject("https://fr.openfoodfacts.org/api/v0/produit/" + idEan + ".json", String.class);

		JsonNode RootNode = mapper.readTree(new StringReader(answerOpenFactFood));

		if (RootNode.path("status").asInt() == 0) {
			throw new ExceptionMessage("OPENFACTFOOD PRODUIT NOT FOUND");
		}
		product = new Product();
		product.setBrand(RootNode.path("product").path("brands").asText().split(",")[0]);
		product.setName(RootNode.path("product").path("product_name_fr").asText());
		product.setEan(new BigInteger(idEan));
		product.setImplementation("");
		product.setUnitName(RootNode.path("product").path("quantity").asText());
		product.setUrl(RootNode.path("product").path("image_url").asText());
		product.setCategory(categoryService.findById(idCategory));
		product = productService.create(product);
		if (product == null)
			throw new ExceptionMessage("ERROR CREATE PRODUCT");
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

}
