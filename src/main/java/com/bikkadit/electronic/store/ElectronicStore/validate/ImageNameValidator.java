package com.bikkadit.electronic.store.ElectronicStore.validate;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ImageNameValidator implements ConstraintValidator<ImageNameValidate,String> {


 private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("message form is Valid :{}",value);



        // logic
        if(value.isBlank()){
            return false;
        }
        else{
        return true;
    }
    }
}
