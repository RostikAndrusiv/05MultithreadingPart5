package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import org.junit.Before;
import org.junit.Test;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.exception.AccountNotFoundException;

import java.math.BigDecimal;

import static org.rostik.andrusiv.entity.CurrencyType.UAH;
import static org.rostik.andrusiv.entity.CurrencyType.USD;

public class AccountNotFountExceptionTest extends TestBase {

    public void thread1() throws InterruptedException {
        accountService.exchange("notValid", currencyExchange, BigDecimal.valueOf(1),3);
    }

    @Test(expected = AccountNotFoundException.class)
    public void test() throws Throwable {
        TestFramework.runManyTimes(new AccountNotFountExceptionTest(), 100);
    }

    @Before
    public void setup(){
        currencyExchange = new CurrencyExchange(UAH, USD, BigDecimal.valueOf(0.15));
    }
}
