package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.rostik.andrusiv.exception.AccountModifiedException;

import java.math.BigDecimal;

@Slf4j
public class AccountModifiedExceptionExchangeTest extends TestBase {

    public void thread1() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 1);
    }

    public void thread2() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 1);
    }

    public void thread3() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 1);
    }

    public void thread4() throws InterruptedException {
        accountService.exchange("test", currencyExchange, BigDecimal.valueOf(1), 1);
    }

    @Test(expected = AccountModifiedException.class)
    public void test() throws Throwable {
        TestFramework.runManyTimes(new AccountModifiedExceptionExchangeTest(), 100);
    }
}
