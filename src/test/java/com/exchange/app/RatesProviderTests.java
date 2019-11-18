package com.exchange.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RatesProviderTests {

    private static final String SEK = "SEK";
    private static final String USD = "USD";
    private static final String EUR = "EUR";

    private Map<String, Double> exchangeRates;

    @BeforeEach
    void setUp() {
        exchangeRates = new HashMap<>() {};
    }

    @Test
    @DisplayName("For default currency (EUR) returns USD rate")
    void test1() {

        //given
        ForeignExchangeRatesApiClient apiClient = Mockito.mock(ForeignExchangeRatesApiClient.class);
        ExchangeRates exchangeRates = initializeExchangeRates();
        Mockito.when(apiClient.getLatestRates()).thenReturn(exchangeRates);

        RatesProvider provider = new RatesProvider(apiClient);

        //when
        Double rateUSD = provider.getExchangeRateInEUR(Currency.getInstance(USD));

        //then
        assertEquals(exchangeRates.rates.get(USD), rateUSD, "USD rate should be included");
    }

    @Test
    @DisplayName("For default currency (EUR) returns all rates")
    void test2() {

        //given
        ForeignExchangeRatesApiClient apiClient = Mockito.mock(ForeignExchangeRatesApiClient.class);
        ExchangeRates exchangeRates = initializeExchangeRates();
        Mockito.when(apiClient.getLatestRates()).thenReturn(exchangeRates);

        RatesProvider provider = new RatesProvider(apiClient);

        //when
        Double rateSEK = provider.getExchangeRateInEUR(Currency.getInstance(SEK));
        Double rateUSD = provider.getExchangeRateInEUR(Currency.getInstance(USD));

        //then
        assertAll(
                () -> assertEquals(exchangeRates.rates.get(USD), rateUSD, "USD rate should be included"),
                () -> assertEquals(exchangeRates.rates.get(SEK), rateSEK, "SEK rate should be included")
        );
    }

    @Test
    void shouldReturnCurrencyExchangeRatesForOtherCurrency() {
        //given
        ForeignExchangeRatesApiClient apiClient = Mockito.mock(ForeignExchangeRatesApiClient.class);
        exchangeRates.put(EUR, 0.8);
        exchangeRates.put(SEK, 15.30);

        ExchangeRates exchangeRates = initializeExchangeRates(USD, new Date(), exchangeRates);
        Mockito.when(apiClient.getLatestRates(USD)).thenReturn(exchangeRates);

        RatesProvider provider = new RatesProvider(apiClient);

        //when
        Double rate = provider.getExchangeRate(Currency.getInstance(SEK), Currency.getInstance(USD));

        //then
        assertTrue(exchangeRates.rates.containsKey(SEK));
        assertEquals(exchangeRates.rates.get(SEK), rate);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyNotSupported() {
        //given
        ForeignExchangeRatesApiClient apiClient = Mockito.mock(ForeignExchangeRatesApiClient.class);
        Mockito.when(apiClient.getLatestRates()).thenThrow(new IllegalArgumentException());

        RatesProvider provider = new RatesProvider(apiClient);

        //then

        IllegalArgumentException actual =
                assertThrows(IllegalArgumentException.class,
                        () -> provider.getExchangeRateInEUR(Currency.getInstance("CHF")));

        assertEquals("Currency is not supported: CHF", actual.getMessage());
    }

    @Test
    void shouldGetRatesOnlyOnce() {
        //given
        ForeignExchangeRatesApiClient apiClient = Mockito.mock(ForeignExchangeRatesApiClient.class);
        ExchangeRates exchangeRates = initializeExchangeRates();
        Mockito.when(apiClient.getLatestRates()).thenReturn(exchangeRates);

        RatesProvider provider = new RatesProvider(apiClient);

        //when
        provider.getExchangeRateInEUR(Currency.getInstance(SEK));

        //then
        Mockito.verify(apiClient, Mockito.times(1)).getLatestRates();
    }

    private ExchangeRates initializeExchangeRates() {
        exchangeRates.put(USD, 1.22);
        exchangeRates.put(SEK, 10.30);
        return initializeExchangeRates(EUR, new Date(), exchangeRates);
    }

    private ExchangeRates initializeExchangeRates(String base) {
        exchangeRates.put(EUR, 1.22);
        exchangeRates.put(SEK, 10.30);
        return initializeExchangeRates(base, new Date(), exchangeRates);
    }

    private ExchangeRates initializeExchangeRates(String base, Date date, Map<String, Double> rates) {
        return new ExchangeRates(base, date, rates);
    }

}