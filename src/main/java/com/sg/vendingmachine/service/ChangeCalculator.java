package com.sg.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute the change to return. The change is composed of a Set of {@link Change}.
 */
public class ChangeCalculator {

    private static ChangeCalculator INSTANCE = null;

    private ChangeCalculator() {
    }

    public static ChangeCalculator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChangeCalculator();
        }
        return INSTANCE;
    }

    public BigDecimal computeRest(BigDecimal money, BigDecimal price) {
        return money.subtract(price).setScale(2, RoundingMode.HALF_UP);
    }

    public List<Change> computeChangeCoins(BigDecimal amount) {
        BigDecimal buffer = amount.setScale(2, RoundingMode.HALF_UP);
        List<Change> result = new ArrayList<>();
        //divideToIntegralValue : returns a BigDecimal whose value is the integer part of the quotient
        for (Coins coin : Coins.getOrderedValues()) {
            Change change = new Change(
                    coin, buffer.divideToIntegralValue(coin.getPrice()).intValue());
            result.add(change);
            buffer = buffer.remainder(coin.getPrice());
        }
        return result;
    }
}
