package com.example.core.data.domain;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransitablePageImpl<T> extends PageImpl<T> {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_RANGE = 2;
    private static final int ELEMENTS_LIMIT = 1000;

    private final long totalElements;
    private final long limitOfElements;
    private final int[] numbers;

    public TransitablePageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, (total > ELEMENTS_LIMIT) ? ELEMENTS_LIMIT : total);
        this.totalElements = total;
        this.limitOfElements = this.getSize() * this.getTotalPages();
        int current = this.getNumber();
        this.numbers = IntStream
                .rangeClosed(Math.max(0, current - PAGE_RANGE), Math.min(current + PAGE_RANGE, getTotalPages() - 1))
                .toArray();
    }

    public boolean isExceedsLimitOfElements() {
        return this.totalElements > this.limitOfElements;
    }

}
