package com.example.core.validation.constraintvalidators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import com.example.core.validation.constraints.Either;
import com.example.core.validation.constraints.Either.CheckType;

public class EitherValidator implements ConstraintValidator<Either, Object> {

    private String[] properties;
    private CheckType checkType;

    @Override
    public void initialize(Either constraintAnnotation) {
        if (ObjectUtils.isEmpty(constraintAnnotation.value())) {
            throw new IllegalArgumentException("candidate values must not be empty.");
        }
        this.properties = constraintAnnotation.value();
        this.checkType = constraintAnnotation.checkType();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // get properties
        Map<String, Object> propertyValues = new HashMap<String, Object>();
        BeanWrapper w = new BeanWrapperImpl(value);
        Arrays.stream(properties).forEach(p -> {
            propertyValues.put(p, w.getPropertyValue(p));
        });

        // check properties
        for (Map.Entry<String, Object> p : propertyValues.entrySet()) {
            if (!checkType.isXX(p.getValue())) {
                return true;
            }
        }

        return false;
    }
}
