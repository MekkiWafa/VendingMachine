package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;

import java.util.List;

/**
 * @author Wafa Mekki
 */
public interface VendingMachineDao {

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String name);

    Item updateItemInventory(Item item) throws VendingMachinePersistenceException;

}
