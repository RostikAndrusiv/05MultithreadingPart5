package org.rostik.andrusiv.dao;

import org.rostik.andrusiv.entity.Account;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.rostik.andrusiv.util.JsonUtils.readJsonFromFile;
import static org.rostik.andrusiv.util.JsonUtils.saveJsonToFile;

public class AccountDao {

    private Lock lock = new ReentrantLock();

    public void save(Account account) {
        StringBuilder sb = new StringBuilder();
        String location = sb.append("c:/exchange-service/accounts/")
                .append(account.getName())
                .append(".json").toString();
        lock.lock();
        try {
            saveJsonToFile(account, location);
        } finally {
            lock.unlock();
        }
    }

    public Optional<Account> load(String accountName) {
        StringBuilder sb = new StringBuilder();
        String location = sb.append("c:/exchange-service/accounts/")
                .append(accountName)
                .append(".json").toString();
        lock.lock();
        try {
            return readJsonFromFile(location, Account.class);
        } finally {
            lock.unlock();
        }
    }
}
