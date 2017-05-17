package com.example.core.validation.constraintvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.example.core.validation.constraints.In;

public class InValidator implements ConstraintValidator<In, CharSequence> {

    private String[] candidates;

    @Override
    public void initialize(In constraintAnnotation) {
        if (ObjectUtils.isEmpty(constraintAnnotation.value())) {
            throw new IllegalArgumentException("candidate values must not be empty.");
        }
        this.candidates = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return ObjectUtils.containsElement(this.candidates, value);
    }

}
