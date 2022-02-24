package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Wafa Mekki
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    private static final int LINE_ELEMENTS_NUMBER = 3;
    private static final String DELIMITER = "::";
    private final String INVENTORY_FILE;
    private Map<String, Item> items = new HashMap<>();

    public VendingMachineDaoFileImpl() {
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String inventoryTextFile) {
        INVENTORY_FILE = inventoryTextFile;
    }


    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadItems();
        return new ArrayList<>(items.values());
    }

    @Override
    public Item getItem(String name) {
        return items.get(name);
    }


    public Item purchaseItem(String name, Item item) {
        items.replace(name, item);
        return item;
    }

    @Override
    public Item updateItemInventory(Item item) throws VendingMachinePersistenceException {
        Item updatedItem = purchaseItem(item.computeKey(), item);
        updateInventoryFile();
        return updatedItem;
    }

    private Item unmarshallItem(String itemAsText) throws VendingMachinePersistenceException {
        List<String> stringTokens = Arrays.asList(itemAsText.split(DELIMITER));
        if (stringTokens.size() == LINE_ELEMENTS_NUMBER) {
            String name = stringTokens.get(0);

            Item itemFromFile = new Item(name);
            BigDecimal price = new BigDecimal(stringTokens.get(1));
            itemFromFile.setPrice(price);
            // itemFromFile.setPrice(new BigDecimal(stringTokens[1]));
            itemFromFile.setInventory(Integer.parseInt(stringTokens.get(2)));

            return itemFromFile;
        }
        throw new VendingMachinePersistenceException("can not read the Inventory line : " + itemAsText +
                ". Please check if the line contains exactly 3 fields");
    }

    private void loadItems() throws VendingMachinePersistenceException {

        try (Scanner scanner = new Scanner(
                new BufferedReader(
                        new FileReader(INVENTORY_FILE)))) {
            // Create Scanner for reading the file

            while (scanner.hasNextLine()) {
                // get the next line in the file
                // unmarshall the line into a item
                Item currentItem = unmarshallItem(scanner.nextLine());
                items.put(currentItem.getName(), currentItem);
            }
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load data into memory.", e);
        }
    }

    private String marshallItem(Item anItem) {
        return anItem.getName() + DELIMITER + anItem.getPrice() + DELIMITER + anItem.getInventory();
    }

    public void updateInventoryFile() throws VendingMachinePersistenceException {

        try (PrintWriter out = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            String itemAsText;
            List<Item> itemList = this.getAllItems();
            for (Item currentItem : itemList) {
                itemAsText = marshallItem(currentItem);
                out.println(itemAsText);
                out.flush();
            }
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }
    }
}
