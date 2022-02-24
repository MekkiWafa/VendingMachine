package com.sg.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * @author Wafa Mekki
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {
    private static final String AUDIT_FILE = "audit.txt";

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        try (PrintWriter out = new PrintWriter(new FileWriter(AUDIT_FILE, true))) {

            out.println(LocalDateTime.now() + " : " + entry);
            out.flush();

        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
        }
    }

}
