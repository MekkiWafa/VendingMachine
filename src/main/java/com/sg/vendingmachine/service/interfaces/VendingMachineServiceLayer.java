package com.sg.vendingmachine.service.interfaces;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.Change;
import com.sg.vendingmachine.service.exceptions.InsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.NoItemInventoryException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wafa Mekki
 */
public interface VendingMachineServiceLayer {

    List<Item> getItems() throws VendingMachinePersistenceException;

    List<Change> purchaseItem(Item item, BigDecimal amount) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException;

    // void updateInventory() throws VendingMachinePersistenceException;
}
