package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.util.JsonUtils;

public class CreateAccountHappyPath extends TestBase{
    public void thread1() throws InterruptedException {
        accountService.createAccount("test1");
    }

    public void thread2() throws InterruptedException {
        accountService.createAccount("test2");
    }

    @Override
    public void finish() {
        Account expected1 = new Account("test1");
        Account expected2 = new Account("test2");

        Account actual1 = JsonUtils.readJsonFromFile("test/exchange-service/accounts/test1.json", Account.class).get();
        Account actual2 = JsonUtils.readJsonFromFile("test/exchange-service/accounts/test2.json", Account.class).get();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void test() throws Throwable {
        TestFramework.runManyTimes(new CreateAccountHappyPath(), 1);
    }
}
