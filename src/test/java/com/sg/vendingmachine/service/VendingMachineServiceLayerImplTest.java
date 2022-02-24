/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.exceptions.InsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.NoItemInventoryException;
import com.sg.vendingmachine.service.interfaces.VendingMachineServiceLayer;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author ASUS
 */
public class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
        /*VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        service = new VendingMachineServiceLayerImpl(dao, auditDao);*/
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        service =
                ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPurchaseItemSuccess() {
        // ARRANGE
        Item item = new Item("WATER", new BigDecimal("1.6"), 2);
        BigDecimal money = new BigDecimal("2");

        // ACT
        try {
            service.purchaseItem(item, money);
        } catch (InsufficientFundsException
                | NoItemInventoryException
                | VendingMachinePersistenceException e) {
            // ASSERT
            fail("Purchase was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testPurchaseItemInsufficientFunds() {
        // ARRANGE
        Item item = new Item("WATER", new BigDecimal("1.6"), 2);
        BigDecimal money = new BigDecimal("1");

        // ACT
        try {
            service.purchaseItem(item, money);
            fail("Expected InsufficientFundsException was not thrown.");
        } catch (VendingMachinePersistenceException
                | NoItemInventoryException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }

    @Test
    public void testPurchaseItemNoInventory() {
        // ARRANGE
        Item item = new Item("COKE", new BigDecimal("1.9"), 0);
        BigDecimal money = new BigDecimal("2");

        // ACT
        try {
            service.purchaseItem(item, money);
            fail("Expected NoItemInventoryException was not thrown.");
        } catch (VendingMachinePersistenceException
                | InsufficientFundsException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    @Test
    public void testGetAllItems() throws Exception {
        // ARRANGE
        Item testClone = new Item("WATER", new BigDecimal("1.6"), 2);

        // ACT & ASSERT
        assertEquals("Should only have one item.",
                1, service.getItems().size());
        assertTrue("The one item should be TWIX BAR.",
                service.getItems().contains(testClone));
    }


}
