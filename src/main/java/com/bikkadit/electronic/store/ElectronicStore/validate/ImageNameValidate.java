package com.bikkadit.electronic.store.ElectronicStore.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValidate {

    // error message
    String message() default "Invalid Image Name!!";

    //represent grooup of constrains
    Class<?>[] groups() default { };

    // additionl information about annotations
    Class<? extends Payload>[] payload() default { };
}
