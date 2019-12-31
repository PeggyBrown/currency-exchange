package com.exchange.app;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public interface ForeignExchangeRatesApiClient {

    ExchangeRates getLatestRates();

    List<ExchangeRates> getLatestRatesForCurrencies(List<String> symbols);

    ExchangeRates getLatestRates(String base);

    ExchangeRates getHistoricalRates(DateTime date);

    List<ExchangeRates> getHistoricalRates(DateTime start_at, DateTime end_at);

    List<ExchangeRates> getHistoricalRates(DateTime start_at, DateTime end_at, List<String> symbols);

    List<ExchangeRates> getHistoricalRates(DateTime start_at, DateTime end_at, String base);
}
