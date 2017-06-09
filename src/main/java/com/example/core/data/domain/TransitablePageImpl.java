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

    private final long totalElements;
    private final long limitOfElements;
    private final int[] numbers;

    public TransitablePageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.totalElements = total;
        this.limitOfElements = Integer.MAX_VALUE;
        this.numbers = calcurateNumbers(this.getNumber());
    }

    public TransitablePageImpl(List<T> content, Pageable pageable, long total, int limit) {
        super(content, pageable, (total > limit) ? limit : total);
        this.totalElements = total;
        this.limitOfElements = this.getSize() * this.getTotalPages();
        this.numbers = calcurateNumbers(this.getNumber());
    }

    public boolean isExceedsLimitOfElements() {
        return this.totalElements > this.limitOfElements;
    }

    private int[] calcurateNumbers(int current) {
        return IntStream.rangeClosed(Math.max(0, current - PAGE_RANGE),
                Math.min(current + PAGE_RANGE, this.getTotalPages() - 1)).toArray();
    }

}
