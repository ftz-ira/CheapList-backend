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
			
		  Seb = restTemplate.getForObject(
	 "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=50.6138111,3.0423598999999997&radius=50000&types=grocery_or_supermarket&name=&key=AIzaSyDizEEeL61KclC1OA9foAkA7SuNBxtFxsA",String.class);
//		  System.out.println(Seb);
		  
		  // Attention JSON powaaaaaaaa
		
		return Seb;
	

	}
	
}
