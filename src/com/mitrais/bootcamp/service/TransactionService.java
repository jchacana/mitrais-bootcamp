package com.mitrais.bootcamp.service;

import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.domain.Transaction;
import com.mitrais.bootcamp.store.TransactionStore;

/**
 * Created by jchacana on 7/20/19.
 */
public class TransactionService {

    private TransactionStore store;

    public TransactionService() {
        this.store = TransactionStore.getInstance();
    }

    public void performTransaction(Transaction transaction) throws Exception {
        Account origin = transaction.getOrigin();
        Account destination = transaction.getDestination();
        Long amount = transaction.getAmount();
        String referenceNumber = transaction.getReferenceNumber();
        switch (transaction.getTransactionType()) {
            case WITHDRAW:
                origin.withdraw(amount);
                break;
            case TRANSFER:
                origin.withdraw(amount);
                destination.deposit(amount);
                break;
            default:
                break;
        }
        store.addTransaction(transaction);
    }
}
