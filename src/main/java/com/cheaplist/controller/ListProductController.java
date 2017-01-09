package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ListProductNotFound;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.View;
import com.cheaplist.service.ListProductService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/lists")
public class ListProductController {

	@Autowired
	private ListProductService listProductService;

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
	public ListProduct AddOneElement(@PathVariable Integer idList, @RequestBody String addElement) throws ListProductNotFound {

			ObjectMapper mapper = new ObjectMapper();
			ListProduct listProduct= null;
			JsonNode rootNode;
			try {
				rootNode = mapper.readTree(new StringReader(addElement));
				int productQuantity = rootNode.path("productQuantity").asInt();
				int idProduct = rootNode.path("idProduct").asInt();
				// Si le JSON est incorrect
				if (productQuantity <1 || idProduct <1) return null;				
				listProduct = listProductService.createOneElement(idList,idProduct,productQuantity);
				System.out.println(listProduct.getId());
				
			} catch (IOException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return listProduct;
	}
	
	/*** REMOVE ONE ELEMENT FROM ONE LIST
	 * 
	 */
	@JsonView(View.ListProduct.class)
	@RequestMapping(value = "/{idList}/element/{idElement}", method = RequestMethod.DELETE)
	public ResponseEntity<String> RemoveOneElement(@PathVariable Integer idList, @PathVariable Integer idElement) throws ListProductNotFound {

		ListProduct listProduct = listProductService.findProductByList(idList.intValue(), idElement.intValue());
		listProductService.delete(listProduct.getId());
		return new ResponseEntity<String>("ELEMENT DELETED",HttpStatus.OK);
	}


}
