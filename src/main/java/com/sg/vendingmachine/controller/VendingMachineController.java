package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.Change;
import com.sg.vendingmachine.service.exceptions.InsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.NoItemInventoryException;
import com.sg.vendingmachine.service.interfaces.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wafa Mekki
 */
public class VendingMachineController {
    private VendingMachineServiceLayer service;
    private VendingMachineView view;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            List<Item> items = service.getItems();
            //display available items
            view.displayItemList(items);
            //display insert money
            BigDecimal money = view.insertMoney();
            //select item
            int selectedItem = view.selectItem();
            //dispaly change coins
            if (selectedItem <= items.size()) {
                purchaseItem(items, money, selectedItem);
            } else {
                exitMessage();
            }

        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void purchaseItem(List<Item> items, BigDecimal money, int selectedItem) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        List<Change> changes = service.purchaseItem(items.get(selectedItem - 1), money);
        view.displayChangeMessage(changes);
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
