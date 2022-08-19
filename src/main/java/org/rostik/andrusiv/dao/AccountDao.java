package org.rostik.andrusiv.dao;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.exception.AccountModifiedException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.rostik.andrusiv.util.JsonUtils.readJsonFromFile;
import static org.rostik.andrusiv.util.JsonUtils.saveJsonToFile;


@Slf4j
public class AccountDao {

    private String baseDir = "c:/";

    //TODO regular hashMap if keys contains threadname???
    private final Map<String, Account> accountsCache = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();
    private final Lock lock = new ReentrantLock();

    public boolean save(Account account, boolean enableCas) {
        String accountName = account.getName();
        String cacheKey = Thread.currentThread().getName() + "accountName";
        String location = buildPathForSave(account);
        lock.lock();
        try {
            if (!enableCas) {
                saveJsonToFile(account, location);
                return true;
            }
            readJsonFromFile(location, Account.class).ifPresent(acc -> {
                if (accountsCache.containsKey(cacheKey) && !acc.equals(accountsCache.get(cacheKey))) {
                    log.info(String.format("%s: Account %s was modified", Thread.currentThread().getName(), accountName));
                    throw new AccountModifiedException(String.format("Account %s was modified", accountName));
                }
            });
            saveJsonToFile(account, location);
            accountsCache.remove(cacheKey);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Optional<Account> load(String accountName, boolean enableCas) {
        String location = buildPathForLoad(accountName);
        String cacheKey = Thread.currentThread().getName() + "accountName";
        lock.lock();
        try {
            if (!enableCas) {
                return readJsonFromFile(location, Account.class);
            }
            Optional<Account> account = readJsonFromFile(location, Account.class);
            account.ifPresentOrElse(accToCache ->
                            accountsCache.put(cacheKey, gson.fromJson(gson.toJson(accToCache), Account.class)),
                    () -> log.info("AccountDao: account {} doesn't exist", accountName));
            return account;
        } finally {
            lock.unlock();
        }
    }

    private String buildPathForSave(Account account) {
        return baseDir + "exchange-service/accounts/" + account.getName() + ".json";
    }

    private String buildPathForLoad(String accountName) {
        return baseDir + "exchange-service/accounts/" + accountName + ".json";
    }
}
