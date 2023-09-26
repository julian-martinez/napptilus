package com.julian.napptilus.prices;

import com.julian.napptilus.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;
    @Mock
    private PriceConverter priceConverter;
    @InjectMocks
    private PriceServiceImpl priceServiceImpl;

    private static final Supplier<PriceVo> PRICE_VO_WITH_HIGHEST_PRIORITY = () -> {
        final PriceVo priceVo = new PriceVo();
        priceVo.setPriceId(4);
        priceVo.setPriority(1000);
        return priceVo;
    };

    private static final Supplier<PriceVo> PRICE_VO = () -> {
        final PriceVo priceVo = new PriceVo();
        priceVo.setPriceId(7);
        priceVo.setPriority(0);
        return priceVo;
    };

    @Test
    void shouldFindPriceReturnPriceWithHighestPriorityWhenNCandidates() {
        final List<Price> priceEntityDummyList = List.of();
        final List<PriceVo> priceVoList = List.of(PRICE_VO.get(), PRICE_VO_WITH_HIGHEST_PRIORITY.get());

        given(this.priceRepository.findPriceBy(any(), any(), any())).willReturn(priceEntityDummyList);
        given(this.priceConverter.convertEntityToVo(priceEntityDummyList)).willReturn(priceVoList);

        assertThat(this.priceServiceImpl.findPriceBy(null, null, null))
                .isEqualTo(PRICE_VO_WITH_HIGHEST_PRIORITY.get());
    }

    @Test
    void shouldFindPriceReturnSamePriceWhenOneCandidate() {
        final List<Price> priceEntityDummyList = List.of();
        final List<PriceVo> priceVoList = List.of(PRICE_VO.get());

        given(this.priceRepository.findPriceBy(any(), any(), any())).willReturn(priceEntityDummyList);
        given(this.priceConverter.convertEntityToVo(priceEntityDummyList)).willReturn(priceVoList);

        assertThat(this.priceServiceImpl.findPriceBy(null, null, null))
                .isEqualTo(PRICE_VO.get());
    }

    @Test
    void shouldFindPriceThrowExceptionWhenZeroCandidates() {
        final List<Price> emptyPriceEntityDummyList = List.of();
        final List<PriceVo> emptyPriceVoList = List.of();

        given(this.priceRepository.findPriceBy(any(), any(), any())).willReturn(emptyPriceEntityDummyList);
        given(this.priceConverter.convertEntityToVo(emptyPriceEntityDummyList)).willReturn(emptyPriceVoList);

        assertThatThrownBy(() -> this.priceServiceImpl.findPriceBy(null, null, null))
                .isInstanceOf(NotFoundException.class);
    }

}