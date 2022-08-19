package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.Currency;
import org.rostik.andrusiv.entity.CurrencyType;
import org.rostik.andrusiv.util.JsonUtils;

import java.math.BigDecimal;
import java.util.List;


public class ExchangeRetryTest extends TestBase {

    public void thread1() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 3);
    }

    public void thread2() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 3);
    }

    public void thread3() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 3);
    }

    public void thread4() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 3);
    }

    @Override
    public void finish() {
        Account expected = new Account("test");
        Currency UAH = new Currency(CurrencyType.UAH, BigDecimal.valueOf(981));
        Currency USD = new Currency(CurrencyType.USD, BigDecimal.valueOf(0.60).setScale(2));
        expected.setCurrencies(List.of(UAH, USD));
        Account actual = JsonUtils.readJsonFromFile("test/exchange-service/accounts/test.json", Account.class).get();

        assertEquals(expected, actual);
    }

    @Test
    public void test() throws Throwable {
        TestFramework.runManyTimes(new ExchangeRetryTest(), 100);
    }
}
