package com.sg.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Coins {

    PENNY(1, "penny"),
    NICKEL(5, "nickel"),
    DIME(10, "dime"),
    QUARTER(25, "quarter");


    private final int value;
    private final String name;

    Coins(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<Coins> getOrderedValues() {
        List<Coins> coins = Arrays.asList(values());
        coins.sort(Comparator.comparing(Coins::getValueDescending));
        return coins;
    }

    public int getValueDescending() {
        return -value;
    }

    public BigDecimal getPrice() {
        return new BigDecimal(value / 100.0).setScale(2, RoundingMode.HALF_UP);
    }
}

