package org.rostik.andrusiv.dao;

import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.CurrencyExchange;

import java.util.Optional;

import static org.rostik.andrusiv.util.JsonUtils.readJsonFromFile;
import static org.rostik.andrusiv.util.JsonUtils.saveJsonToFile;

public class CurrencyExchangeDao {

    public void save(CurrencyExchange currencyExchange) {
        StringBuilder sb = new StringBuilder();
        String location = sb.append("c:/exchange-service/currencyExchange/")
                .append(currencyExchange.getCurrencyFrom())
                .append("_")
                .append(currencyExchange.getCurrencyTo())
                .append(".json").toString();

        saveJsonToFile(currencyExchange, location);
    }

    public Optional<CurrencyExchange> load(String currencyExchange) {
        StringBuilder sb = new StringBuilder();
        String location = sb.append("c:/exchange-service/currencyExchange/")
                .append(currencyExchange)
                .append(".json").toString();

        return readJsonFromFile(location, CurrencyExchange.class);
    }
}
