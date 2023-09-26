package com.julian.napptilus.prices;

import java.time.LocalDateTime;

public interface PriceService {

    PriceVo findPriceBy(
            final LocalDateTime applyDate,
            final Long productId,
            final Long brandId);
}
