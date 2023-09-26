package com.julian.napptilus.prices;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceVo {

    private Integer priceId;
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;
}
