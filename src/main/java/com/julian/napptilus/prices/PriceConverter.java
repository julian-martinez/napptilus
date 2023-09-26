package com.julian.napptilus.prices;

import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PriceConverter {

    private final Mapper mapper;

    public PriceVo convertEntityToVo(final Price entity) {
        return mapper.map(entity, PriceVo.class);
    }

    public List<PriceVo> convertEntityToVo(final List<Price> entityList) {
        return entityList.stream()
                .map(this::convertEntityToVo)
                .collect(Collectors.toList());
    }
}
