package org.rostik.andrusiv;

import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.service.AccountService;
import org.rostik.andrusiv.service.CurrencyExchangeService;

import java.math.BigDecimal;

import static org.rostik.andrusiv.entity.CurrencyType.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AccountService service = new AccountService();
        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();
        CurrencyExchange currencyExchange = new CurrencyExchange(UAH, USD, BigDecimal.valueOf(0.15));
        CurrencyExchange currencyExchange1 = new CurrencyExchange(UAH, EUR, BigDecimal.valueOf(0.2));
        currencyExchangeService.createCurrencyExchange(currencyExchange);
        currencyExchangeService.createCurrencyExchange(currencyExchange1);
        service.createAccount("test");
        service.addCurrency("test", UAH, BigDecimal.valueOf(1000), 3);
        service.exchange("test", currencyExchange, BigDecimal.valueOf(1), 3);
    }
}
