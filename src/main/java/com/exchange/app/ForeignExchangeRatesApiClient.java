package com.exchange.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ForeignExchangeRatesApiClient {

    ExchangeRates getLatestRates();

    ExchangeRates getLatestRatesForCurrencies(List<String> symbols);

    ExchangeRates getLatestRates(String base);

    ExchangeRates getHistoricalRates(Date date);

    ExchangeRates getHistoricalRates(Date start_at, Date end_at);

    ExchangeRates getHistoricalRates(Date start_at, Date end_at, List<String> symbols);

    ExchangeRates getHistoricalRates(Date start_at, Date end_at, String base);
}

class ExchangeRates {
    String base;
    Date date;
    Map<String, Double> rates;

    public ExchangeRates(String base, Date date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }
}
