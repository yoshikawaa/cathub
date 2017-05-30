package com.example.core.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

import com.example.core.validation.constraintvalidators.EitherValidator;

@Documented
@Constraint(validatedBy = { EitherValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Either {
    String message() default "{com.example.core.validator.constraints.Either.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value() default {};

    CheckType checkType()

    default CheckType.Null;

    @Documented
    @Target({ TYPE, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @interface List {
        Required[] value();
    }

    public enum CheckType {
        Null {
            @Override
            public boolean isXX(Object value) {
                return value == null;
            }
        },
        Empty {
            @Override
            public boolean isXX(Object value) {
                return StringUtils.isEmpty(value);
            }
        },
        Blank {
            @Override
            public boolean isXX(Object value) {
                return value == null || StringUtils.isEmpty(StringUtils.trimWhitespace(value.toString()));
            }
        };

        public abstract boolean isXX(Object value);
    }
}
