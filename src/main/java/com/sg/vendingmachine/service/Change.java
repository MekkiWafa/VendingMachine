package com.sg.vendingmachine.service;

import java.util.Objects;

public class Change {
    private final Coins coins;
    private final int quantity;

    public Change(Coins coins, int quantity) {
        this.coins = coins;
        this.quantity = quantity;
    }


    public Coins getCoins() {
        return coins;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Change that = (Change) o;
        return coins == that.coins &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coins, quantity);
    }
}
