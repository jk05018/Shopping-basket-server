package org.prgms.shoppingbasket.server.common.validation.email;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<EmailConstraint, String> {
	private static final String EMAIL_REGREX = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		return s != null && Pattern.matches(EMAIL_REGREX, s);
	}

	public static boolean validateEmail(String email){
		return Pattern.matches(EMAIL_REGREX, email);
	}
}
