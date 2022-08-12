package org.rostik.andrusiv;

import org.rostik.andrusiv.dao.CurrencyExchangeDao;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.service.AccountService;
import org.rostik.andrusiv.service.CurrencyExchangeService;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.rostik.andrusiv.entity.CurrencyType.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {
        AccountService service = new AccountService();
        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();
        CurrencyExchange currencyExchange = new CurrencyExchange(UAH, USD, BigDecimal.valueOf(0.15));
        CurrencyExchange currencyExchange1 = new CurrencyExchange(UAH, EUR, BigDecimal.valueOf(0.2));
        currencyExchangeService.createCurrencyExchange(currencyExchange);
        currencyExchangeService.createCurrencyExchange(currencyExchange1);
        service.createAccount("Rost");
        service.exchange("Rost", currencyExchange, BigDecimal.valueOf(15));

    }
}
