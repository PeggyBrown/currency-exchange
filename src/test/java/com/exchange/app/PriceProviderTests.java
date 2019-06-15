package com.exchange.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PriceProviderTests {

    private final PriceProvider provider = new PriceProvider();

    @Test
    void addition() {
        assertEquals(100.00, provider.getPriceInEUR("SEK"));
    }

}