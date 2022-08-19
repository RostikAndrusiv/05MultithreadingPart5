package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;
import org.rostik.andrusiv.exception.AccountModifiedException;

import java.math.BigDecimal;

import static org.rostik.andrusiv.entity.CurrencyType.USD;

public class AccountModifiedAddCurrencyExceptionTest extends TestBase{

    public void thread1() throws InterruptedException {
        accountService.addCurrency("test", USD, BigDecimal.valueOf(100), 3);
    }

    public void thread2() throws InterruptedException {
        accountService.addCurrency("test", USD, BigDecimal.valueOf(100), 3);
    }

    public void thread3() throws InterruptedException {
        accountService.addCurrency("test", USD, BigDecimal.valueOf(100), 3);
    }

    public void thread4() throws InterruptedException {
        accountService.addCurrency("test", USD, BigDecimal.valueOf(100), 3);
    }

    @Test(expected = AccountModifiedException.class)
    public void test() throws Throwable {
        TestFramework.runManyTimes(new AccountModifiedExceptionExchangeTest(), 100);
    }
}
