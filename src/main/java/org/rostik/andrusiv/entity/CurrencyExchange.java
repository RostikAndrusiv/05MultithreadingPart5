package org.rostik.andrusiv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchange {

    private CurrencyType currencyFrom;

    private CurrencyType currencyTo;

    private BigDecimal exchangeRate;
}
