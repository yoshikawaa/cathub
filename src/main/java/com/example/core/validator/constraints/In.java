package com.example.core.validator.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.core.validator.constraintvalidators.InValidator;

@Documented
@Constraint(validatedBy = { InValidator.class })
@Target({ METHOD, FIELD, PARAMETER, TYPE_PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface In {
    String message() default "{com.example.core.validator.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value() default {};

    @Documented
    @Target({ METHOD, FIELD, PARAMETER, TYPE_PARAMETER })
    @Retention(RUNTIME)
    @interface List {
        In[] value();
    }
}
