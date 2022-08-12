package org.rostik.andrusiv;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rostik.andrusiv.dao.AccountDao;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.service.AccountService;
import org.rostik.andrusiv.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

public class AppTest {

    private final AccountService accountService = new AccountService();

    private final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    private static final CountDownLatch countDownLatch = new CountDownLatch(2);

    @Test
    public void exchangeTest() throws InterruptedException {
        CurrencyExchange currencyExchange = currencyExchangeService.loadCurrencyExchange("UAH_USD");
        Thread thread1 = new Thread(() -> {
            try {
                accountService.exchange("Rost", currencyExchange, BigDecimal.valueOf(1));
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                accountService.exchange("Rost", currencyExchange, BigDecimal.valueOf(10));
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        countDownLatch.await();
    }
}
