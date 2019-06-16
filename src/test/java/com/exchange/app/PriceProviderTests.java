package com.exchange.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Currency;

class PriceProviderTests {

    private final PriceProvider provider = new PriceProvider();

    @Test
    void shouldReturnPriceInEUR() {

        assertEquals(100.00, provider.getPriceInEUR(Currency.getInstance("SEK")));
    }

    @Test
    void shouldReturnCurrencyExchangeRate() {

        assertEquals(0.8, provider.getExchangeRate(Currency.getInstance("SEK"), Currency.getInstance("USD")));
    }

}