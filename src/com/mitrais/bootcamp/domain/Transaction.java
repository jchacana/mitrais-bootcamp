package com.mitrais.bootcamp.domain;

import java.time.LocalDateTime;

/**
 * Created by jchacana on 7/20/19.
 */
public class Transaction {

    private TransactionType transactionType;
    private Account origin;
    private Account destination;
    private Long amount;
    private String referenceNumber;
    private LocalDateTime transactionDate = LocalDateTime.now();


    public Transaction() {
    }

    public Transaction(TransactionType transactionType, Account origin, Account destination, Long amount) {
        this.transactionType = transactionType;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
    }

    public Transaction(TransactionType transactionType, Account origin, Long amount) {
        this.transactionType = transactionType;
        this.origin = origin;
        this.amount = amount;
    }

    public Transaction(TransactionType transactionType, Account origin, Account destination, Long amount, String referenceNumber) {
        this.transactionType = transactionType;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (getTransactionType() != that.getTransactionType()) return false;
        if (getOrigin() != null ? !getOrigin().equals(that.getOrigin()) : that.getOrigin() != null) return false;
        if (getDestination() != null ? !getDestination().equals(that.getDestination()) : that.getDestination() != null)
            return false;
        if (getAmount() != null ? !getAmount().equals(that.getAmount()) : that.getAmount() != null) return false;
        if (getReferenceNumber() != null ? !getReferenceNumber().equals(that.getReferenceNumber()) : that.getReferenceNumber() != null)
            return false;
        return getTransactionDate() != null ? getTransactionDate().equals(that.getTransactionDate()) : that.getTransactionDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getTransactionType() != null ? getTransactionType().hashCode() : 0;
        result = 31 * result + (getOrigin() != null ? getOrigin().hashCode() : 0);
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getReferenceNumber() != null ? getReferenceNumber().hashCode() : 0);
        result = 31 * result + (getTransactionDate() != null ? getTransactionDate().hashCode() : 0);
        return result;
    }

    public enum TransactionType {
        WITHDRAW,TRANSFER
    }
}