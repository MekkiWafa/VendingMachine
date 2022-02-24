package com.sg.vendingmachine.dao;

/**
 * @author Wafa Mekki
 */
public interface VendingMachineAuditDao {

    void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
