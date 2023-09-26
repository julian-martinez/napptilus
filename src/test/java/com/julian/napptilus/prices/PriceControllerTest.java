package com.julian.napptilus.prices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PriceControllerTest {

    private static final String GET_PRICE_ENDPOINT = "/api/prices/";
    private static final String APPLY_DATE_PARAM = "applyDate";
    private static final String PRODUCT_ID_PARAM = "productId";
    private static final String BRAND_ID_PARAM = "brandId";
    private static final String PRODUCT_ID_VALUE = "35455";
    private static final String BRAND_ID_VALUE = "1";


    @Autowired
    private PriceService priceService;

    @Autowired
    private MockMvc mockMvc;


    public static Stream<Arguments> priceData() {
        return Stream.of(
                Arguments.of("2020-06-14T10:00:00", 1),
                Arguments.of("2020-06-14T16:00:00", 2),
                Arguments.of("2020-06-14T21:00:00", 1),
                Arguments.of("2020-06-15T10:00:00", 3),
                Arguments.of("2020-06-16T21:00:00", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("priceData")
    void shouldReturnPrice(final String applyDate, final Integer priceId) throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get(GET_PRICE_ENDPOINT)
                        .param(APPLY_DATE_PARAM, applyDate)
                        .param(PRODUCT_ID_PARAM, PRODUCT_ID_VALUE)
                        .param(BRAND_ID_PARAM, BRAND_ID_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceId", is(priceId)))
                .andReturn();

    }

    @Test
    void shouldResponseNotFound() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get(GET_PRICE_ENDPOINT)
                        .param(APPLY_DATE_PARAM, "2023-01-01T00:00:00")
                        .param(PRODUCT_ID_PARAM, PRODUCT_ID_VALUE)
                        .param(BRAND_ID_PARAM, BRAND_ID_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}