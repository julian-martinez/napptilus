package com.julian.napptilus.prices;

import com.julian.napptilus.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(final PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/")
    protected ResponseEntity<PriceVo> findPrice(
            @RequestParam final LocalDateTime applyDate,
            @RequestParam final Long productId,
            @RequestParam final Long brandId) {
        return new ResponseEntity<>(this.priceService.findPriceBy(applyDate, productId, brandId), HttpStatus.OK);
    }

    @ExceptionHandler({ NotFoundException.class })
    protected ResponseEntity<PriceVo> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
