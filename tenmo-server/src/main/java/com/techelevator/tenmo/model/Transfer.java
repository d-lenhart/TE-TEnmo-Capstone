package com.techelevator.tenmo.model;

public class Transfer {

    int transferId;
    int transferTypeId;
    int transferStatusId;
    int accountFrom;
    int accountTo;
    double amount;
    int accountId;
    int userId;
    double balance;



    public int getTransferId() { return transferId; }
    public void setTransferId(int transferId) { this.transferId = transferId; }
    public int getTransferTypeId() { return transferTypeId; }
    public void setTransferTypeId(int transferTypeId) { this.transferTypeId = transferTypeId; }
    public int getTransferStatusId() { return transferStatusId; }
    public void setTransferStatusId(int transferStatusId) { this.transferStatusId = transferStatusId; }
    public int getAccountFrom() { return accountFrom; }
    public void setAccountFrom(int accountFrom) { this.accountFrom = accountFrom; }
    public int getAccountTo() { return accountTo; }
    public void setAccountTo(int accountTo) { this.accountTo = accountTo; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

}
