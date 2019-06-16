package com.exchange.app;

import java.util.Currency;

public class PriceProvider
{
    public PriceProvider(){}

    public double getPriceInEUR(Currency requested){
        return 100.00;
    }

    public double getExchangeRate(Currency requested, Currency exchanged){
        return 0.8;
    }
}
