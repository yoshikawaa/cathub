package com.example.core.thymeleaf.dialect;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;

public class SpringAdditionalDialect extends SpringStandardDialect {

    private Map<String, Object> expressionObjects;

    public SpringAdditionalDialect(Map<String, Object> expressionObjects) {
        super();
        this.expressionObjects = expressionObjects;
    }

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        Map<String, Object> expressionObjects = super.getAdditionalExpressionObjects(processingContext);
        expressionObjects = Stream.of(expressionObjects, this.expressionObjects)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return expressionObjects;
    }

}
