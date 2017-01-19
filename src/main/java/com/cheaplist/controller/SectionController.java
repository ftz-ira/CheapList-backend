package com.cheaplist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ErrorResponse;
import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Category;
import com.cheaplist.model.Section;
import com.cheaplist.model.View;
import com.cheaplist.service.SectionService;
import com.fasterxml.jackson.annotation.JsonView;

//Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/sections")
public class SectionController {

	@Autowired
	private SectionService sectionService;

	/*** READ ALL SECTION ****/
	@JsonView(View.Section.class)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Section>> sectionAll() throws ExceptionMessage {
		ArrayList<Section> sectionList = (ArrayList<Section>) sectionService.findAll();
		if (sectionList.isEmpty())
			throw new ExceptionMessage("ERROR ID SECTION NON FOUND");
		for (Section section : sectionList) {
			Set<Category> categories = section.getCategories();
			for (Category category : categories) {
				category.setProducts(null);
			}
		}
		return new ResponseEntity<List<Section>> (sectionList,HttpStatus.OK);

	}

	/*** READ ONE SECTION BY ID ****/
	@JsonView(View.Section.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Section> sectionOne(@PathVariable Integer id) throws ExceptionMessage {
		Section section = sectionService.findById(id.intValue());
		if (section == null)
			throw new ExceptionMessage("ERROR ID SECTION NON FOUND");
		return new ResponseEntity<Section>(section, HttpStatus.OK);

	}

	/*** READ ALL CATEGORIES BY ID SECTION 
	 * @throws ExceptionMessage ****/
	@JsonView(View.SectionCategory.class)
	@RequestMapping(value = "/{id}/categories", method = RequestMethod.GET)
	public ResponseEntity<Section>  sectionAllCategories(@PathVariable Integer id) throws ExceptionMessage {
		Section section = sectionService.findById(id.intValue());
		if (section == null)
			throw new ExceptionMessage("ERROR ID SECTION NON FOUND");
		return new ResponseEntity<Section>(section, HttpStatus.OK);
	}
	
	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}

}
