package com.example.core.thymeleaf.expression;

import java.math.BigDecimal;

public class Maths {

    public BigDecimal add(Object a, Object b) {
        return new BigDecimal(String.valueOf(a)).add(new BigDecimal(String.valueOf(b)));
    }

    public BigDecimal subtract(Object a, Object b) {
        return new BigDecimal(String.valueOf(a)).subtract(new BigDecimal(String.valueOf(b)));
    }

    public BigDecimal multiply(Object a, Object b) {
        return new BigDecimal(String.valueOf(a)).multiply(new BigDecimal(String.valueOf(b)));
    }

    public BigDecimal divide(Object a, Object b) {
        return new BigDecimal(String.valueOf(a)).divide(new BigDecimal(String.valueOf(b)));
    }

    public BigDecimal remainder(Object a, Object b) {
        return new BigDecimal(String.valueOf(a)).remainder(new BigDecimal(String.valueOf(b)));
    }
    
    public boolean eq(Object a, Object b) {
        return this.compareTo(a, b) == 0;
    }
    
    public boolean gt(Object a, Object b) {
        return this.compareTo(a, b) < 0;
    }

    public boolean ge(Object a, Object b) {
        return this.compareTo(a, b) <= 0;
    }

    public boolean lt(Object a, Object b) {
        return this.compareTo(a, b) > 0;
    }

    public boolean le(Object a, Object b) {
        return this.compareTo(a, b) >= 0;
    }

    private int compareTo(Object a, Object b) {
        return new BigDecimal(String.valueOf(a)).compareTo(new BigDecimal(String.valueOf(b)));
    }
}
