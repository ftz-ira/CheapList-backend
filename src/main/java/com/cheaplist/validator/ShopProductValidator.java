package com.cheaplist.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cheaplist.model.ShopProduct;

@Component
public class ShopProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ShopProduct.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {			  

	ShopProduct shopProduct = (ShopProduct) target;
		
	ValidationUtils.rejectIfEmpty(errors,"price","price missing");
	ValidationUtils.rejectIfEmpty(errors,"product","product missing");	
	ValidationUtils.rejectIfEmpty(errors,"shop","shop missing");
	System.out.println("Seb :"+errors.hasErrors());
	if (errors.hasErrors()) return;
	
	if (shopProduct.getPrice() <= 0)
		errors.rejectValue("price","PRICE MUST GREATER THAN ZERO");
	if (shopProduct.getProduct().getId() <= 0)
		errors.rejectValue("product.id","ID PRODUCT MUST GREATER THAN ZERO");
	if (shopProduct.getShop().getId() <= 0)
		errors.rejectValue("shop.id","ID SHOP MUST GREATER THAN ZERO");	
	}

}
