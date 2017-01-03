package com.cheaplist.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cheaplist.model.Member;

@Component
public class MemberValidator implements Validator {
	

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Member member = (Member) target;
		
		ValidationUtils.rejectIfEmpty(errors,"name","name missing");
		ValidationUtils.rejectIfEmpty(errors,"login","login missing");
		ValidationUtils.rejectIfEmpty(errors,"password","password missing");
		ValidationUtils.rejectIfEmpty(errors,"email","rmail missing");
			
		/*Shop shop = (Shop) target;
		
		Integer emplNumber = shop.getEmplNumber();
		
		ValidationUtils.rejectIfEmpty(errors, "name", "shop.name.empty");
		ValidationUtils.rejectIfEmpty(errors, EMPLOYEES_NUMBER, "shop.emplNumber.empty");
		
		if (emplNumber != null && emplNumber < 1)
			errors.rejectValue(EMPLOYEES_NUMBER, "shop.emplNumber.lessThenOne");
*/
	}

}
