package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Wafa Mekki
 */
public class Item {
    private String name;
    private BigDecimal price;
    private int inventory;

    public Item(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.inventory = quantity;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    // override equals() and hashCode() to help test functionality
    // will allow us to compare one Item object to another without writing extra code
    // whenever you do unit testing, override the equals() and hashCode() in the applicable dto
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.inventory, inventory) == 0 &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, inventory);
    }

    // for logging aspect, format how the entry looks by overriding the built in toString() method
    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", inventory=" + inventory +
                '}';
    }

    public String computeKey() {
        return name;
    }
}
