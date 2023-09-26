package com.julian.napptilus.prices;


import com.julian.napptilus.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final PriceConverter priceConverter;

    public PriceServiceImpl(
            final PriceRepository priceRepository,
            final PriceConverter priceConverter) {
        this.priceRepository = priceRepository;
        this.priceConverter = priceConverter;
    }

    @Override
    public PriceVo findPriceBy(final LocalDateTime applyDate, final Long productId, final Long brandId) {
        final List<PriceVo> priceVoList = this.priceConverter.convertEntityToVo(this.priceRepository.findPriceBy(applyDate, productId, brandId));
        return this.selectPriceByHighestPriority(priceVoList);
    }

    private PriceVo selectPriceByHighestPriority(final List<PriceVo> priceList) {
        return priceList.stream()
                .max(Comparator.comparingInt(PriceVo::getPriority))
                .orElseThrow(NotFoundException::new);
    }
}
