package com.cheaplist.controller;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cheaplist.exception.OpenfactfoodNotFound;
import com.cheaplist.model.Openfactfood;
import com.cheaplist.service.OpenfactfoodService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/off")
public class OpenfactfoodController {

	/*
	 * Seb = restTemplate.getForObject(
	 * "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false",
	 * String.class,"90 rue Baudin, 92300 Levallois Perret");
	 */

	@Autowired
	private OpenfactfoodService openfactfoodService;

	/** test Google Map **/
	@RequestMapping(value = "/go")
	public String googleTest() {


		return "ok";
	}

}
