package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.exceptions.InsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.NoItemInventoryException;
import com.sg.vendingmachine.service.interfaces.VendingMachineServiceLayer;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wafa Mekki
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private ChangeCalculator calculator = ChangeCalculator.getInstance();

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getItems() throws VendingMachinePersistenceException {
        /*
        We first create the stream with the stream method and then use filter to keep only Item
        objects having the inventory >0 in the stream. We then finally use collect to put them into a List.
        The Collectors.toList() call specifically puts the remaining things in the Stream into a List.
         */
        return dao.getAllItems().stream()
                .filter((p) -> p.getInventory() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Change> purchaseItem(Item item, BigDecimal money) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        Item itemToPurchase = dao.getItem(item.getName());
        if (itemToPurchase.getInventory() == 0) {
            auditDao.writeAuditEntry(
                    "Item " + item.getName() + " IS no longer available.");
            throw new NoItemInventoryException(
                    "ERROR: Could not purchase Item. "
                            + itemToPurchase.getName()
                            + " has 0 inventory");
        }
        checkFunds(item, money);
        List<Change> coinsToReturn = ChangeCalculator.getInstance()
                .computeChangeCoins(calculator.computeRest(money, item.getPrice()));

        itemToPurchase.setInventory((itemToPurchase.getInventory() - 1));
        dao.updateItemInventory(itemToPurchase);

        // The item was successfully purchased, now write to the audit log
        addAuditLine(item, coinsToReturn);

        return coinsToReturn;
    }

    private void addAuditLine(Item item, List<Change> coinsToReturn) throws VendingMachinePersistenceException {
        String sb = "Item " + item.getName() + " is purchased at Price : " + item.getPrice() + "."
                + "Change : " +
                coinsToReturn.stream()
                        .map(c -> c.getQuantity() + " of " + c.getCoins())
                        .collect(Collectors.joining(", "));
        auditDao.writeAuditEntry(sb);
    }

    private void checkFunds(Item item, BigDecimal funds) throws InsufficientFundsException, VendingMachinePersistenceException {
        if (funds.compareTo(item.getPrice()) < 0) {
            auditDao.writeAuditEntry(
                    "Insufficient funds to purchase this item " + item.getName()
                            + "  for " + item.getPrice() + ". Your current funds is " + funds);

            throw new InsufficientFundsException(
                    "ERROR: The funds are not sufficient. You enter " + funds + " The Item Price " + item.getPrice());
        }
    }
}
