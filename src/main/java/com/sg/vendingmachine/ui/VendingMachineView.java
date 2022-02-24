package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.Change;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * @author Wafa Mekki
 */
public class VendingMachineView {
    private UserIO io;
    private int counter = 1;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayItemList(List<Item> itemList) {
        io.print("======Welcome to the vending machine=====");
        io.print("      Available Products ");
        if (itemList.isEmpty()) {
            io.print("The vending machine is empty");
        } else {
            NumberFormat nF = NumberFormat.getCurrencyInstance();
            nF.setCurrency(Currency.getInstance(Locale.US));
            for (Item currentItem : itemList) {
                String itemInfo = String.format("%d. %s - Price: %s ",
                        counter,
                        currentItem.getName(),
                        nF.format(currentItem.getPrice()));
                io.print(itemInfo);
                counter++;
            }
            io.print(counter + ". Exit");
        }
    }

    public BigDecimal insertMoney() {
        return io.readBigDecimal("Please insert money.", BigDecimal.ZERO);
    }

    public int selectItem() {
        return io.readInt("Please select an item from the above choices.", 1, counter);
    }

    public void displayChangeMessage(List<Change> changes) {
        changes.forEach(s -> io.print(s.getCoins().name() + " : " + s.getQuantity()));
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
}
