package org.rostik.andrusiv.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.rostik.andrusiv.dao.CurrencyExchangeDao;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.entity.CurrencyType;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
public class CurrencyExchangeService {

    private static final CurrencyExchangeDao dao = new CurrencyExchangeDao();

    public CurrencyExchange createCurrencyExchange(CurrencyExchange currencyExchange) {
        if (currencyExchange == null) {
            throw new IllegalArgumentException("Arguments should be not null");
        }
        CurrencyType from = currencyExchange.getCurrencyFrom();
        CurrencyType to = currencyExchange.getCurrencyTo();
        BigDecimal exchangeRate = currencyExchange.getExchangeRate();

        if (ObjectUtils.anyNull(from, to, exchangeRate)) {
            throw new RuntimeException("CurrencyExchange is not valid");
        }

        CurrencyExchange exchange = new CurrencyExchange(from, to, exchangeRate);
        dao.save(exchange);
        return exchange;
    }

    public CurrencyExchange loadCurrencyExchange(String name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Arguments should be not null");
        }
        CurrencyExchange exchange = dao.load(name).orElseThrow(() -> new RuntimeException("no currencyExchange fonund with name: " + name));
        return exchange;
    }
}
