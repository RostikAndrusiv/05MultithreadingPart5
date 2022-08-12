package org.rostik.andrusiv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    private CurrencyType currencyType;

    private BigDecimal value;

}
