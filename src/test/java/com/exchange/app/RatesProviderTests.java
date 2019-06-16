package com.exchange.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class RatesProviderTests {

    private final ForeignExchangeRatesApiClient apiClient = Mockito.mock(ForeignExchangeRatesApiClient.class);
    private final RatesProvider provider = new RatesProvider(apiClient);

    @Test
    void shouldReturnPriceInEUR() {
        //given
        ExchangeRates rates = createRates();
        Mockito.when(apiClient.getLatestRates()).thenReturn(rates);

        //when
        Double rate = provider.getExchangeRateInEUR(Currency.getInstance("SEK"));

        //then
        assertTrue(rates.rates.containsKey("SEK"));
        assertEquals(rates.rates.get("SEK"), rate);
    }

    @Test
    void shouldReturnCurrencyExchangeRate() {
        //given
        Map<String, Double> exchangeRates = new HashMap<String, Double>(){};
        exchangeRates.put("EUR", 0.8);
        exchangeRates.put("SEK", 15.30);

        ExchangeRates rates = createRates("USD", new Date(), exchangeRates);
        Mockito.when(apiClient.getLatestRates("USD")).thenReturn(rates);

        //when
        Double rate = provider.getExchangeRate(Currency.getInstance("SEK"), Currency.getInstance("USD"));

        //then
        assertTrue(rates.rates.containsKey("SEK"));
        assertEquals(rates.rates.get("SEK"), rate);
    }

    private ExchangeRates createRates(){
        Map<String, Double> rates = new HashMap<String, Double>(){};
        rates.put("USD", 1.22);
        rates.put("SEK", 10.30);
        return new ExchangeRates("EUR", new Date(), rates);
    }

    private ExchangeRates createRates(String base, Date date, Map<String, Double> rates){
        return new ExchangeRates(base, date, rates);
    }

}