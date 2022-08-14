package org.rostik.andrusiv.service;

import lombok.extern.slf4j.Slf4j;
import org.rostik.andrusiv.dao.AccountDaoCas;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.Currency;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.entity.CurrencyType;
import org.rostik.andrusiv.exception.AccountModifiedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class AccountServiceCas {

    private static final AccountDaoCas dao = new AccountDaoCas();

    public Account createAccount(String name) {
        Account acc = new Account(name);
        dao.load(name, false).ifPresentOrElse(account -> log.info("Account with name {} already exist", account.getName()),
                () -> dao.save(acc, false));
        return acc;
    }


    public Account addCurrency(String name, CurrencyType currencyType, BigDecimal currencyValue, int retries) {
        Account account = dao.load(name, true).orElseThrow(() -> new RuntimeException("no account with name: " + name));
        var currency = new Currency(currencyType, currencyValue);
        List<Currency> currencies = new CopyOnWriteArrayList<>();
        currencies.add(currency);
        Optional.ofNullable(account.getCurrencies()).ifPresentOrElse(
                c -> addAmountToCurrency(account, currencyType, currencyValue),
                () -> account.setCurrencies(currencies));
        try {
            dao.save(account, true);
        } catch (AccountModifiedException ex) {
            if (retries > 0) {
                log.info(String.format("%s : retrying... retries left: %s ", Thread.currentThread().getName(), retries));
                addCurrency(name, currencyType, currencyValue, retries - 1);
            } else log.info(String.format("%s : account %s was not updated", Thread.currentThread().getName(), name));
        }

        return account;
    }

    public Account exchange(String name, CurrencyExchange currencyExchange, BigDecimal amount, int retries)  {
        Account account = dao.load(name, true).orElseThrow(() -> new RuntimeException("found no account with name: " + name));
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyExchange.getCurrencyFrom()))
                .findAny()
                .ifPresentOrElse(c -> exchangeCurrencies(account, currencyExchange, amount),
                        () -> log.info("You have no currency type: {} to convert from", currencyExchange.getCurrencyFrom()));
        try {
            dao.save(account, true);
            log.info(String.format("%s: account %s was updated successfully", Thread.currentThread().getName(), name));
        } catch (AccountModifiedException ex) {
            if (retries > 0) {
                log.info(String.format("%s : retrying... retries left: %s ", Thread.currentThread().getName(), retries));
                exchange(name, currencyExchange, amount, retries - 1);
            } else log.info(String.format("%s : account %s was not updated", Thread.currentThread().getName(), name));
        }
        return account;
    }

    private void exchangeCurrencies(Account account, CurrencyExchange currencyExchange, BigDecimal amount) {
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyExchange.getCurrencyFrom()))
                .forEach(currency -> currency.setValue(currency.getValue().subtract(amount)));
        account.getCurrencies().stream()
                .filter(currency -> currency.getCurrencyType().equals(currencyExchange.getCurrencyTo()))
                .findAny()
                .ifPresentOrElse(currency -> currency.setValue(
                        currency.getValue().add(amount.multiply(currencyExchange.getExchangeRate()))),
                        () -> account.getCurrencies().add(new Currency(
                                currencyExchange.getCurrencyTo(), amount.multiply(currencyExchange.getExchangeRate()))));
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
