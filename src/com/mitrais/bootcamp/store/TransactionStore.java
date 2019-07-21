package com.mitrais.bootcamp.store;

import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.domain.Transaction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jchacana on 7/20/19.
 */
public class TransactionStore {

    private Set<Transaction> transactions;
    private static TransactionStore instance;

    private TransactionStore() {

        transactions = new LinkedHashSet<>();
    }

    public static TransactionStore getInstance() {
        if(instance == null) {
            instance = new TransactionStore();
        }
        return instance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction getTransactionByRefNumber(String refNumber) {
        Transaction transaction = transactions
                .stream()
                .filter(trx -> trx.getReferenceNumber().equals(refNumber))
                .findFirst()
                .orElse(null);
        return transaction;
    }

    public Set<Transaction> getAllTransactions() {
        return transactions;
    }

    /**
     * Important here to preserve order as Collectors.toSet does not guarantee such thing
     * @param numberOfTransactions
     * @return
     */
    public Set<Transaction> getLastNTransactions(Integer numberOfTransactions) {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .limit(numberOfTransactions)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<Transaction> getLastNTransactions(Account account, Integer numberOfTransactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getOrigin().getAccountNumber().equals(account.getAccountNumber()))
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .limit(numberOfTransactions)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
