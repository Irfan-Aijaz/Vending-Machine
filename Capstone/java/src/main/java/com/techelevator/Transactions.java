package com.techelevator;

import java.math.BigDecimal;

public class Transactions {

    private String balance = "";

    public Transactions(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void deposit(String amountToDeposit) {
        // Use BigDecimal for more accurate deposit
        BigDecimal startBalance = new BigDecimal(balance);
        BigDecimal depositAmount = new BigDecimal(amountToDeposit);
        BigDecimal finalBalance = startBalance.add(depositAmount);
        balance = finalBalance.toString();
    }

    public void subtractFromBalance(String amountToWithdraw) {
        // Use BigDecimal for more accurate withdraw
        BigDecimal startBalance = new BigDecimal(balance);
        BigDecimal withdrawAmount = new BigDecimal(amountToWithdraw);
        BigDecimal finalBalance = startBalance.subtract(withdrawAmount);
        balance = finalBalance.toString();
    }

}
