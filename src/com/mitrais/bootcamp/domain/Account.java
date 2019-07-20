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

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getName() != null ? !getName().equals(account.getName()) : account.getName() != null) return false;
        if (getPin() != null ? !getPin().equals(account.getPin()) : account.getPin() != null) return false;
        if (getBalance() != null ? !getBalance().equals(account.getBalance()) : account.getBalance() != null)
            return false;
        return getAccountNumber() != null ? getAccountNumber().equals(account.getAccountNumber()) : account.getAccountNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getPin() != null ? getPin().hashCode() : 0);
        result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
        result = 31 * result + (getAccountNumber() != null ? getAccountNumber().hashCode() : 0);
        return result;
    }
}
