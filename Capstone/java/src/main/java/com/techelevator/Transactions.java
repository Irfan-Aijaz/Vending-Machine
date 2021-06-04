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
        BigDecimal startBalance = new BigDecimal(balance);
        BigDecimal depositAmount = new BigDecimal(amountToDeposit);
        BigDecimal finalBalance = startBalance.add(depositAmount);
        balance = finalBalance.toString();
    }

    public void subtractFromBalance(String amountToWithdraw) {
        BigDecimal startBalance = new BigDecimal(balance);
        BigDecimal withdrawAmount = new BigDecimal(amountToWithdraw);
        BigDecimal finalBalance = startBalance.subtract(withdrawAmount);
        balance = finalBalance.toString();
    }

}
