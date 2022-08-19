package org.rostik.andrusiv;

import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;
import org.rostik.andrusiv.exception.AccountExistsException;

public class AccountServiceCreateAccountExceptionIfExistTest extends TestBase {

    public void thread1() throws InterruptedException {
        accountService.createAccount("test");
    }

    public void thread2() throws InterruptedException {
        accountService.createAccount("test");
    }

    @Test(expected = AccountExistsException.class)
    public void test() throws Throwable {
        TestFramework.runManyTimes(new AccountServiceCreateAccountExceptionIfExistTest(), 1);
    }
}
