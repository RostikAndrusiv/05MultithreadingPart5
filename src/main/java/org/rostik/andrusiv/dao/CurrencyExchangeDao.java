package org.rostik.andrusiv.dao;

import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.CurrencyExchange;

import java.util.Optional;

import static org.rostik.andrusiv.util.JsonUtils.readJsonFromFile;
import static org.rostik.andrusiv.util.JsonUtils.saveJsonToFile;

public class CurrencyExchangeDao {

    private String baseDir = "c:/";

    public void save(CurrencyExchange currencyExchange) {
        String location = buildPathForSave(currencyExchange);
        saveJsonToFile(currencyExchange, location);
    }

    public Optional<CurrencyExchange> load(String currencyExchange) {
        String location = buildPathForLoad(currencyExchange);
        return readJsonFromFile(location, CurrencyExchange.class);
    }

    private String buildPathForSave(CurrencyExchange currencyExchange) {
        StringBuilder sb = new StringBuilder();
        return sb.append(baseDir)
                .append("exchange-service/currencyExchange/")
                .append(currencyExchange.getCurrencyFrom())
                .append("_")
                .append(currencyExchange.getCurrencyTo())
                .append(".json").toString();
    }

    private String buildPathForLoad(String currencyExchange) {
        StringBuilder sb = new StringBuilder();
        return sb.append(baseDir)
                .append("exchange-service/currencyExchange/")
                .append(currencyExchange)
                .append(".json").toString();
    }
}
