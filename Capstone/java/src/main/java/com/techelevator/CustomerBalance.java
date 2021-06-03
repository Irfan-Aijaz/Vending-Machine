package com.techelevator;

import java.math.BigDecimal;

public class CustomerBalance {

    private String balance;

    public CustomerBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public String deposit(String amountToDeposit) {
        BigDecimal startBalance = new BigDecimal(balance);
        BigDecimal depositAmount = new BigDecimal(amountToDeposit);
        BigDecimal finalBalance = startBalance.add(depositAmount);
        return finalBalance.toString();
    }

    public String makeChange(String amountToWithdraw) {
        BigDecimal startBalance = new BigDecimal(balance);
        BigDecimal withdrawAmount = new BigDecimal(amountToWithdraw);
        BigDecimal finalBalance = startBalance.subtract(withdrawAmount);
        return finalBalance.toString();
    }

}
