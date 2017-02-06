package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.ListProduct;
import com.cheaplist.model.Product;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.repository.ListProductRepository;

@Service
public class ListProductServiceImpl implements ListProductService {

	@Resource
	private ListProductRepository listProductRepository;

	@Autowired
	private ShoppingListService shoppingListService;

	@Autowired
	private ProductService productService;

	/******* INSERT ONE ELEMENT INTO ONE LIST *********/
	@Override
	@Transactional
	public ListProduct create(ListProduct listProduct) {
		ListProduct createdListProduct = listProduct;
		return listProductRepository.save(createdListProduct);
	}

	/******* INSERT ONE ELEMENT INTO ONE LIST *********/

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ListProduct createOneElement(int idShoppingList, int idproduct, int quantity) throws ExceptionMessage {
		ShoppingList shoppingList = shoppingListService.findById(idShoppingList);
		Product product = productService.findById(idproduct);
		product.setImplementation("");

		if (shoppingList == null || product == null)
			throw new ExceptionMessage();

		ListProduct createdListProduct = new ListProduct();
		createdListProduct.setIsInBasket(false);
		createdListProduct.setProduct(product);
		createdListProduct.setProductQuantity(quantity);
		createdListProduct.setShoppingList(shoppingList);
		return listProductRepository.save(createdListProduct);
	}

	@Override
	@Transactional
	public ListProduct findById(int id) {
		return listProductRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ListProduct delete(int id) throws ExceptionMessage {
		ListProduct deletedListProduct = listProductRepository.findOne(id);

		if (deletedListProduct == null)
			throw new ExceptionMessage();

		listProductRepository.delete(deletedListProduct);
		return deletedListProduct;
	}

	@Override
	@Transactional
	public List<ListProduct> findAll() {
		return listProductRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ListProduct update(ListProduct listProduct) throws ExceptionMessage {
		ListProduct updatedListProduct = listProductRepository.findOne(listProduct.getId());

		if (updatedListProduct == null)
			throw new ExceptionMessage();

		updatedListProduct.setId(listProduct.getId());
		updatedListProduct.setProductQuantity(listProduct.getProductQuantity());
		return updatedListProduct;
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public ListProduct patch(int idList, int idElement, ListProduct listProduct) throws ExceptionMessage {
		ListProduct updatedListProduct = listProductRepository.findProductByList(idList, idElement);
		if (updatedListProduct == null)
			throw new ExceptionMessage();

		if (listProduct.getProduct() != null)
			updatedListProduct.setProduct(listProduct.getProduct());

		if (listProduct.getProductQuantity() != null)
			updatedListProduct.setProductQuantity(listProduct.getProductQuantity());

		if (listProduct.getShoppingList() != null)
			updatedListProduct.setShoppingList(listProduct.getShoppingList());

		if (listProduct.getIsInBasket() != null)
			updatedListProduct.setIsInBasket(listProduct.getIsInBasket());

		return updatedListProduct;
	}

	@Override
	@Transactional
	public ListProduct findProductByList(int idList, int idElement) {
		return listProductRepository.findProductByList(idList, idElement);
	}

	@Override
	@Transactional
	public List<ListProduct> findProductsByList(int idList) {
		return listProductRepository.findProductsByList(idList);
	}

	@Override
	@Transactional
	public double findPrice(Integer idList, int idShop) {
		return listProductRepository.findPrice(idList, idShop);
	}

	@Override
	@Transactional
	public long findMissing(Integer idList, int idShop) {
		return listProductRepository.findMissing(idList, idShop);
	}

	@Override
	@Transactional
	public long countElement(Integer idList) {
		return listProductRepository.countElement(idList);
	}

	@Override
	public List<ListProduct> findElementByListBtProduct(int idList, int idProduct) {
		return listProductRepository.findElementByListBtProduct(idList, idProduct);
	}

}
