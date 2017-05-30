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

    private int[] numbers;

    public TransitablePageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);

        int current = pageable.getPageNumber();
        numbers = IntStream
                .rangeClosed(Math.max(0, current - PAGE_RANGE), Math.min(current + PAGE_RANGE, getTotalPages() - 1))
                .toArray();
    }

}
