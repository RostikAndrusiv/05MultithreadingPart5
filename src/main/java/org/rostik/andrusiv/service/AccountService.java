package org.rostik.andrusiv.service;

import lombok.extern.slf4j.Slf4j;
import org.rostik.andrusiv.dao.AccountDao;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.Currency;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.entity.CurrencyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class AccountService {

    private static final AccountDao dao = new AccountDao();

    public synchronized Account createAccount(String name) {
        if (dao.load(name).equals(Optional.empty())) {
            Account acc = new Account(name);
            dao.save(acc);
            return acc;
        }
        throw new RuntimeException("account exist");
    }


    public synchronized Account addCurrency(String name, CurrencyType currencyType, BigDecimal currencyValue) {
        Account account = dao.load(name).orElseThrow(() -> new RuntimeException("no account with name: " + name));
        var currency = new Currency(currencyType, currencyValue);
        List<Currency> currencies = new CopyOnWriteArrayList<>();
        currencies.add(currency);
        Optional.ofNullable(account.getCurrencies()).ifPresentOrElse(
                c -> addAmountToCurrency(account, currencyType, currencyValue),
                () -> account.setCurrencies(currencies));
        dao.save(account);
        return account;
    }

    public synchronized Account exchange(String name, CurrencyExchange currencyExchange, BigDecimal amount) {
        Account account = dao.load(name).orElseThrow(() -> new RuntimeException("no account with name: " + name));
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyExchange.getCurrencyFrom()))
                .findAny()
                .ifPresentOrElse(c -> exchangeCurrencies(account, currencyExchange, amount),
                        () -> log.info("You have no currency type: {}", currencyExchange.getCurrencyFrom()));
        dao.save(account);
        return account;
    }

    private void exchangeCurrencies(Account account, CurrencyExchange currencyExchange, BigDecimal amount) {
        var newCurrency = new Currency(currencyExchange.getCurrencyTo(), amount);
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyExchange.getCurrencyFrom()))
                .forEach(currency -> currency.setValue(currency.getValue().subtract(amount)));
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyExchange.getCurrencyTo()))
                .findAny()
                .ifPresentOrElse(currency -> currency.setValue(
                        currency.getValue().add(amount.multiply(currencyExchange.getExchangeRate()))),
                        () -> account.getCurrencies().add(newCurrency));
    }

    private void addAmountToCurrency(Account account, CurrencyType currencyType, BigDecimal currencyValue) {
        var newCurrency = new Currency(currencyType, currencyValue);
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyType))
                .findFirst()
                .ifPresentOrElse(c -> setNewValueToCurrency(account, currencyType, currencyValue),
                        () -> account.getCurrencies().add(newCurrency));
    }

    //TODO ask about good approach
    private void setNewValueToCurrency(Account account, CurrencyType currencyType, BigDecimal currencyValue) {
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyType))
                .forEach(currency -> currency.setValue(currency.getValue().add(currencyValue)));
    }
}
