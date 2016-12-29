package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.OpenfactfoodNotFound;
import com.cheaplist.model.Openfactfood;
import com.cheaplist.repository.OpenfactfoodRepository;

@Service
public class OpenfactfoodServiceImpl implements OpenfactfoodService {
	
	@Resource
	private OpenfactfoodRepository openfactfoodRepository;

	@Override
	@Transactional
	public Openfactfood create(Openfactfood openfactfood) {
		Openfactfood createdOpenfactfood = openfactfood;
		return openfactfoodRepository.save(createdOpenfactfood);
	}
	
	@Override
	@Transactional
	public Openfactfood findById(int id) {
		return openfactfoodRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=OpenfactfoodNotFound.class)
	public Openfactfood delete(int id) throws OpenfactfoodNotFound {
		Openfactfood deletedShop = openfactfoodRepository.findOne(id);
		
		if (deletedShop == null)
			throw new OpenfactfoodNotFound();
		
		openfactfoodRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<Openfactfood> findAll() {
		return openfactfoodRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=OpenfactfoodNotFound.class)
	public Openfactfood update(Openfactfood openfactfood) throws OpenfactfoodNotFound {
		Openfactfood updatedOpenfactfood = openfactfoodRepository.findOne(openfactfood.getId());
		updatedOpenfactfood.setId(openfactfood.getId());
		updatedOpenfactfood.setBrands(openfactfood.getBrands());
		updatedOpenfactfood.setCategorie1(openfactfood.getCategorie1());
		updatedOpenfactfood.setCategorie2(openfactfood.getCategorie2());
		updatedOpenfactfood.setCategorie3(openfactfood.getCategorie3());	
		updatedOpenfactfood.setCategorie4(openfactfood.getCategorie4());
		updatedOpenfactfood.setCategorie5(openfactfood.getCategorie5());
		updatedOpenfactfood.setCategorie6(openfactfood.getCategorie6());	
		updatedOpenfactfood.setCategorie7(openfactfood.getCategorie7());
		updatedOpenfactfood.setName(openfactfood.getName());
		updatedOpenfactfood.setGeneric(openfactfood.getGeneric());
		updatedOpenfactfood.setTruecategory(openfactfood.getTruecategory());
		updatedOpenfactfood.setQuantity(openfactfood.getQuantity());
		updatedOpenfactfood.setEan(openfactfood.getEan());
		updatedOpenfactfood.setUrl(openfactfood.getUrl());
		updatedOpenfactfood.setVolume(openfactfood.getVolume());
		
		
		return updatedOpenfactfood;
	}

}
