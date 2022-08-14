package org.rostik.andrusiv;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.service.AccountServiceCas;
import org.rostik.andrusiv.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

public class AppTest {

    private final AccountServiceCas accountService = new AccountServiceCas();

    private final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    private static final CountDownLatch countDownLatch = new CountDownLatch(15);

    @Test
    public void exchangeTest() throws InterruptedException {
        CurrencyExchange currencyExchange = currencyExchangeService.loadCurrencyExchange("UAH_USD");

        Thread thread1 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread4 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread5 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread6 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread7 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread8 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread9 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread10 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread11 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread12 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread13 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread14 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread15 = new Thread(() -> {
            try {
                accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        thread11.start();
        thread12.start();
        thread13.start();
        thread14.start();
        thread15.start();
        countDownLatch.await();
    }
}
