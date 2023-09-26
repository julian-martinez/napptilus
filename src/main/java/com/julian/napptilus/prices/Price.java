package com.julian.napptilus.prices;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;
    @Column
    private Long brandId;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
    @Column
    private Long productId;
    @Column
    private Integer priority;
    @Column
    private BigDecimal price;
    @Column
    private String currency;
}
