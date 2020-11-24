package validator;

import entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @auther Skay
 * @date 2020/11/16 10:27
 * @description
 */

@Component
public class IsNull implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        ValidationUtils.rejectIfEmpty(errors, "username", "user.username.required");
        ValidationUtils.rejectIfEmpty(errors, "password", "user.password.required");
    }
}
