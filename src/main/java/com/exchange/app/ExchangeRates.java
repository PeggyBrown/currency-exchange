package com.exchange.app;

import org.joda.time.DateTime;

import java.util.Map;

class ExchangeRates {
    private String base;
    private DateTime date;
    private Map<String, Double> rates;

    public ExchangeRates(String base, DateTime date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Double get(String currency) {
        return rates.get(currency);
    }
}
