package com.cheaplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cheaplist.service.OpenfactfoodService;

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
System.out.println("Seb");

		return "ok";
	}

}
