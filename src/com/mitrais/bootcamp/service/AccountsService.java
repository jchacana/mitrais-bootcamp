package com.mitrais.bootcamp.service;

import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.store.AccountStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by jchacana on 7/20/19.
 */
public class AccountsService {

    private AccountStore accountStore;

    public AccountsService() {
        accountStore = AccountStore.getInstance();
    }

    public Set<Account> readAccountsFromFile(String fileName) {
        try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(line -> {
                String[] accountData = line.split(",");
                Account account = new Account(accountData[0], accountData[1], Long.parseLong(accountData[2]), accountData[3]);
                addAccount(account);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllAccounts();
    }

    public void addAccount(Account account) {
        try {
            accountStore.addAccount(account);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<Account> getAllAccounts(){
        return accountStore.getAccounts();
    }
}
