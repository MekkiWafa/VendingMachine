/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Wafa Mekki
 */
public class VendingMachineDaoFileImplTest {
    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    //We will use the setUp method to put our system into a known good state before each test.
    @Before
    public void setUp() {
        String testFile = "testinventory.txt";
        // Use the FileWriter to quickly blank the file
        //new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }


    @After
    public void tearDown() {
    }


    @Test
    public void testGetAllItems() throws Exception {
        //ARRANGE :Create two Item objects to the DAO
        // Create our first item
        Item firstItem = new Item("TWIX BAR");
        firstItem.setPrice(new BigDecimal("1.89"));
        firstItem.setInventory(4);

        // Create our second item
        Item secondItem = new Item("WATER");
        secondItem.setPrice(new BigDecimal("1.45"));
        secondItem.setInventory(8);

        //ACT:  Get all of the Item objects from the DAO

        // Retrieve the list of all items within the DAO
        List<Item> allItems = testDao.getAllItems();

        //ASSERT: Check to see that the DAO returned the 2 objects
        // First check the general contents of the list
        assertNotNull("The list of items must not null", allItems);
        assertEquals("List of items should have 5 items.", 5, allItems.size());

        // Then the specifics
        assertTrue("The list of items should include TWIX.", testDao.getAllItems().contains(firstItem));
        assertTrue("The list of items should include WATER.", testDao.getAllItems().contains(secondItem));

    }


    @Test
    public void testGetItem() throws Exception {
        // Create our method test inputs
        String name = "TWIX BAR";
        // Create our first item
        Item anItem = new Item(name, new BigDecimal("1.89"), 4);
        List<Item> allItems = testDao.getAllItems();
        // Get the item from the DAO
        Item retrievedItem = testDao.getItem(name);

        // Check the data is equal
        assertEquals("Checking name.",
                anItem.getName(),
                retrievedItem.getName());
        assertEquals("Checking item price.",
                anItem.getPrice(),
                retrievedItem.getPrice());
        assertEquals("Checking item inventory.",
                anItem.getInventory(),
                retrievedItem.getInventory());
    }

    @Test
    public void testUpdateItemInventory() throws Exception {
        //ARRANGE
        List<Item> allItems = testDao.getAllItems();
        Item twix_bar = testDao.getItem("WATER");
        twix_bar.setInventory(8);


        //ACT
        Item updatedItem = testDao.updateItemInventory(twix_bar);

        // Retrieve the updated item within the DAO
        Item retrievedItem = testDao.getItem(updatedItem.getName());

        Assert.assertEquals(retrievedItem.getInventory(), 8);
    }


}
