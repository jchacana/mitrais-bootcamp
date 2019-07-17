package com.mitrais.bootcamp.domain;

/**
 * Created by jchacana on 7/10/19.
 */
public class Account {

    private String name;
    private String pin;
    private Long balance;
    private String accountNumber;

    public Account() {
    }

    public Account(String name, String pin, Long balance, String accountNumber) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void withdraw(Long amount) throws Exception {
        if((balance - amount) < 0 )
            throw new Exception("Insufficient balance $" + balance);
        if(amount > 1000)
            throw new Exception("Maximum amount to withdraw is $1000");
        if(amount < 1)
            throw new Exception("Minimum amount to withdraw is $1");
        balance = balance - amount;
    }

    public void deposit(Long amount) {
        balance += amount;
    }
}
