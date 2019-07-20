package com.mitrais.bootcamp.store;

import com.mitrais.bootcamp.domain.Account;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jchacana on 7/20/19.
 */
public class AccountStore {

    private static Set<Account> accounts;
    private static AccountStore store;

    private AccountStore() {
        accounts = new HashSet<>();
    }

    public static AccountStore getInstance() {
        if(store == null) {
            store = new AccountStore();
        }
        return store;
    }

    public void addAccount(Account account) throws Exception {
        if(accountExists(account.getAccountNumber()))
            throw new Exception("There can't be 2 different accounts with the same Account Number");
        if(accountExists(account))
            throw new Exception("There can't be duplicated records " + account);
        accounts.add(account);
    }

    public boolean accountExists(Account account) {
        return accounts.contains(account);
    }

    public boolean accountExists(String accountNumber) {
        try {
            Long.parseLong(accountNumber);
            for(Account account1: accounts) {
                if(account1.getAccountNumber().equals(accountNumber))
                    return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Account getAccount(String accountNumber) throws Exception {
        try {
            Long.parseLong(accountNumber);
            for(Account account1: accounts) {
                if(account1.getAccountNumber().equals(accountNumber))
                    return account1;
            }
            throw new Exception("Invalid account");
        } catch (NumberFormatException e) {
            throw new Exception("Invalid account");
        }
    }

    public Account getAndValidateAccount(String accountNumber, String pin) {
        for(Account account: accounts) {
            if(account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin))
                return account;
        }
        return null;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
}
