package com.example.core.validation.constraintvalidators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

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
    private boolean onlyOne;

    @Override
    public void initialize(Either constraintAnnotation) {
        if (ObjectUtils.isEmpty(constraintAnnotation.value())) {
            throw new IllegalArgumentException("candidate values must not be empty.");
        }
        this.properties = constraintAnnotation.value();
        this.checkType = constraintAnnotation.checkType();
        this.onlyOne = constraintAnnotation.onlyOne();
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
        Stream<Entry<String, Object>> stream = propertyValues.entrySet().stream();
        if (onlyOne) {
            return stream.filter(p -> !checkType.isXX(p.getValue())).count() == 1;
        } else {
            return stream.anyMatch(p -> !checkType.isXX(p.getValue()));
        }
    }
}
