package com.julian.napptilus.prices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query(
            "SELECT p FROM Price p WHERE " +
                    "(:applyDate BETWEEN p.startDate AND p.endDate) AND " +
                    "p.productId = :productId AND " +
                    "p.brandId = :brandId"
    )
    List<Price> findPriceBy(
            @Param("applyDate") final LocalDateTime applyDate,
            @Param("productId") final Long productId,
            @Param("brandId") final Long brandId);
}
