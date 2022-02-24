package com.sg.vendingmachine.service;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ChangeTest {

    @Test
    public void computeChange() {
    }

    @Test
    public void computeChangeCoins() {

        List<Change> concreteChanges = ChangeCalculator.getInstance().computeChangeCoins(new BigDecimal("4.25"));
        Assert.assertEquals(concreteChanges.size(), 4);
        Assert.assertEquals(concreteChanges.get(0), new Change(Coins.QUARTER, 17));
        Assert.assertEquals(concreteChanges.get(1), new Change(Coins.DIME, 0));
        Assert.assertEquals(concreteChanges.get(2), new Change(Coins.NICKEL, 0));
        Assert.assertEquals(concreteChanges.get(3), new Change(Coins.PENNY, 0));

    }

    @Test
    public void computeChangeCoins2() {

        List<Change> concreteChanges = ChangeCalculator.getInstance().computeChangeCoins(new BigDecimal(4.15));
        Assert.assertEquals(concreteChanges.size(), 4);
        Assert.assertEquals(concreteChanges.get(0), new Change(Coins.QUARTER, 16));
        Assert.assertEquals(concreteChanges.get(1), new Change(Coins.DIME, 1));
        Assert.assertEquals(concreteChanges.get(2), new Change(Coins.NICKEL, 1));
        Assert.assertEquals(concreteChanges.get(3), new Change(Coins.PENNY, 0));

    }

    @Test
    public void computeChangeCoins3() {

        List<Change> concreteChanges = ChangeCalculator.getInstance().computeChangeCoins(new BigDecimal(4.16));
        Assert.assertEquals(concreteChanges.size(), 4);
        Assert.assertEquals(concreteChanges.get(0), new Change(Coins.QUARTER, 16));
        Assert.assertEquals(concreteChanges.get(1), new Change(Coins.DIME, 1));
        Assert.assertEquals(concreteChanges.get(2), new Change(Coins.NICKEL, 1));
        Assert.assertEquals(concreteChanges.get(3), new Change(Coins.PENNY, 1));

    }
}