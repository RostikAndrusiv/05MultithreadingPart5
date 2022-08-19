package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.Currency;
import org.rostik.andrusiv.entity.CurrencyType;
import org.rostik.andrusiv.util.JsonUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.rostik.andrusiv.entity.CurrencyType.USD;

public class AddCurrencyTest extends TestBase {

    public void thread1() throws InterruptedException {
        accountService.addCurrency("test", USD, BigDecimal.valueOf(100), 3);
    }

    @Override
    public void finish() {
        Account expected = new Account("test");
        Currency UAH = new Currency(CurrencyType.UAH, BigDecimal.valueOf(985));
        Currency USD = new Currency(CurrencyType.USD, BigDecimal.valueOf(100));
        expected.setCurrencies(List.of(UAH, USD));
        Account actual = JsonUtils.readJsonFromFile("test/exchange-service/accounts/test.json", Account.class).get();

        assertEquals(expected, actual);
    }

    @Test
    public void test() throws Throwable {
        TestFramework.runManyTimes(new AddCurrencyTest(), 100);
    }
}
