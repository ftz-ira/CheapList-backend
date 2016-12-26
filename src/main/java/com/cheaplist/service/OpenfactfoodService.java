package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.OpenfactfoodNotFound;
import com.cheaplist.model.Openfactfood;

public interface OpenfactfoodService {
	
	public Openfactfood create(Openfactfood openfactfood);
	public Openfactfood delete(int id) throws OpenfactfoodNotFound;
	public List<Openfactfood> findAll();
	public Openfactfood update(Openfactfood openfactfood) throws OpenfactfoodNotFound;
	public Openfactfood findById(int id);

}
