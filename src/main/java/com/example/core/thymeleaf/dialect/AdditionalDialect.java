package com.example.core.thymeleaf.dialect;

import java.util.Map;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

public class AdditionalDialect extends AbstractDialect implements IExpressionEnhancingDialect {

    private Map<String, Object> expressionObjects;

    public AdditionalDialect(Map<String, Object> expressionObjects) {
        super();
        this.expressionObjects = expressionObjects;
    }

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        return expressionObjects;
    }

    @Override
    public String getPrefix() {
        return null;
    }

}
