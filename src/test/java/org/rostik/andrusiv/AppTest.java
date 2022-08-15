package org.rostik.andrusiv;

import static org.junit.Assert.assertTrue;
import static org.rostik.andrusiv.entity.CurrencyType.*;
import static org.rostik.andrusiv.entity.CurrencyType.UAH;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.exception.AccountModifiedException;
import org.rostik.andrusiv.service.AccountService;
import org.rostik.andrusiv.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AppTest {
    private static final AccountService accountService = new AccountService();

    private static final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    private static final CountDownLatch countDownLatch = new CountDownLatch(3);

    private static final CurrencyExchange currencyExchange = currencyExchangeService.loadCurrencyExchange("UAH_USD");

    @Test(expected = AccountModifiedException.class)
    public void exchangeTest() throws InterruptedException {
        CurrencyExchange currencyExchange = new CurrencyExchange(UAH, USD, BigDecimal.valueOf(0.15));
        CurrencyExchange currencyExchange1 = new CurrencyExchange(UAH, EUR, BigDecimal.valueOf(0.2));
        currencyExchangeService.createCurrencyExchange(currencyExchange);
        currencyExchangeService.createCurrencyExchange(currencyExchange1);
        accountService.createAccount("aaaa");
        accountService.addCurrency("aaaa", UAH, BigDecimal.valueOf(1000), 3);

        List<Runnable> taskList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            taskList.add(()-> createTask());
        }
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (Runnable task: taskList) {
            service.execute(task);
        }
        System.out.println(Thread.currentThread().getState());
        countDownLatch.await();
        System.out.println(Thread.currentThread().getState());
        Thread.sleep(15_000);
        System.out.println(Thread.currentThread().getState());
        shutdownAndAwaitTermination(service);
    }

    private static void createTask() {
        try {
            log.info(Thread.currentThread().getName() + "Exchange test start.....");
            accountService.exchange("aaaa", currencyExchange, BigDecimal.valueOf(1),3);
            countDownLatch.countDown();
            log.info(Thread.currentThread().getName() + "Exchange test end.....");

        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        // Disable new tasks from being submitted
        pool.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(15, TimeUnit.SECONDS)) {
                // Cancel currently executing tasks forcefully
                pool.shutdownNow();
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(15, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ex) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
