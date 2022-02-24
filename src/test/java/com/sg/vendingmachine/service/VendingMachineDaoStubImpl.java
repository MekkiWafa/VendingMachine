/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ASUS
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    private List<Item> itemList = new ArrayList<>();

    public VendingMachineDaoStubImpl() {
        itemList.add(new Item("WATER", new BigDecimal("1.6"), 2));
        itemList.add(new Item("COKE", new BigDecimal("1.9"), 0));
    }

    public VendingMachineDaoStubImpl(Item testItem) {
        this.itemList.add(testItem);
    }

    @Override
    public List<Item> getAllItems() {
        return itemList;
    }

    @Override
    public Item getItem(String name) {
        Optional<Item> collect = itemList.stream().filter(i -> i.getName().equals(name)).findFirst();
        return collect.orElse(null);
    }

    @Override
    public Item updateItemInventory(Item item) {
        Optional<Item> itemStream = itemList.stream().filter(i -> i.equals(item)).findFirst();
        if (itemStream.isPresent()) {
            itemList.remove(itemStream.get());
            itemList.add(item);
        }
        return item;
    }

}
