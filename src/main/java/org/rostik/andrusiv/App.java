package org.rostik.andrusiv;

import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.service.AccountServiceCas;
import org.rostik.andrusiv.service.CurrencyExchangeService;

import java.math.BigDecimal;

import static org.rostik.andrusiv.entity.CurrencyType.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AccountServiceCas service = new AccountServiceCas();
        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();
        CurrencyExchange currencyExchange = new CurrencyExchange(UAH, USD, BigDecimal.valueOf(0.15));
        CurrencyExchange currencyExchange1 = new CurrencyExchange(UAH, EUR, BigDecimal.valueOf(0.2));
        currencyExchangeService.createCurrencyExchange(currencyExchange);
        currencyExchangeService.createCurrencyExchange(currencyExchange1);
        service.createAccount("aaaa");
        service.addCurrency("aaaa", UAH, BigDecimal.valueOf(1000), 3);
        service.exchange("aaaa", currencyExchange, BigDecimal.valueOf(15), 3);
    }
}
