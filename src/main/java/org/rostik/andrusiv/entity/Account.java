package org.rostik.andrusiv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Account {

    private String name;

    private List<Currency> currencies;

    public Account(String name) {
        this.name = name;
    }

}
