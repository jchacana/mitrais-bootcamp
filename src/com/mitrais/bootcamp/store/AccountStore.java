package com.mitrais.bootcamp.store;

import com.mitrais.bootcamp.domain.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchacana on 7/20/19.
 */
public class AccountStore {

    private static List<Account> accounts;
    private static AccountStore store;

    private AccountStore() {
        accounts = new ArrayList<>();
    }

    public static AccountStore getInstance() {
        if(store == null) {
            store = new AccountStore();
        }
        return store;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account getAccount(Integer index) {
        return accounts.get(index);
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

}
