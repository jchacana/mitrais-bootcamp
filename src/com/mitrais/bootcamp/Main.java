package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.domain.Transaction;
import com.mitrais.bootcamp.service.AccountsService;
import com.mitrais.bootcamp.service.TransactionService;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final int ACCT_LENGHT = 6;
    private static final int PIN_LENGHT = 6;

    private static Scanner scanner;

    private AccountsService accountsService;

    private TransactionService transactionService;

    public static void main(String[] args) {
        Main program = new Main();

        program.initializeAccounts(args);

        boolean show;
        do {
            show = program.showWelcomeScreen();
        } while (show);
    }

    private void initializeAccounts(String[] args) {
        Account johnDoe = new Account("John Doe", "012108", 100L, "112233");
        Account janeDoe = new Account("Jane Doe", "932012", 30L, "112244");

        accountsService.addAccount(johnDoe);
        accountsService.addAccount(janeDoe);

        accountsService.readAccountsFromFile(args[0]);
    }

    public Main() {
        accountsService = new AccountsService();
        transactionService = new TransactionService();
        scanner = new Scanner(System.in);
    }

    private boolean showWelcomeScreen()  {
        clearScreen();
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if(accountNumber.length() != ACCT_LENGHT) {
            System.out.println("Account Number should have "+ACCT_LENGHT+" digits length");
            return true;
        }
        if(!accountNumber.matches("^[0-9]*$")) {
            System.out.println("Account Number should only contains numbers");
            return true;
        }
        if(pin.length() != PIN_LENGHT) {
            System.out.println("PIN should have "+ PIN_LENGHT +" digits length");
            return true;
        }
        if(!pin.matches("^[0-9]*$")) {
            System.out.println("PIN should only contains numbers");
            return true;
        }

        Account account = validateAccount(accountNumber, pin);
        if(account == null) {
            System.out.println("Invalid Account Number/PIN");
            return true;
        }

        printTransactionScreen(account);

        return false;
    }

    private void printTransactionScreen(Account account) {
        boolean shouldContinue = false;
        do {
            shouldContinue = printMenu(account);
        } while (shouldContinue);
    }

    private boolean printMenu(Account account) {
        clearScreen();
        System.out.print(
                "1. Withdraw\n" +
                "2. Fund Transfer\n" +
                "3. Exit\n" +
                "Please choose option[3]:");
        int option = scanner.nextInt();
        scanner.nextLine(); //To avoid moving directly to the end
        boolean shouldContinue = false;
        switch (option) {
            case 1:
                shouldContinue = printWithdrawMenu(account);
                break;
            case 2:
                shouldContinue = printFundTransferScreen(account);
                break;
            case 3:
                return false;
            default:
                return true;
        }
        return shouldContinue;
    }

    private boolean printFundTransferScreen(Account origin) {
        clearScreen();
        System.out.print(
                "Please enter destination account and \n" +
                "press enter to continue or \n" +
                "press cancel (Esc) to go back to Transaction: ");
        String accountNumber = scanner.nextLine();
        if("".equals(accountNumber)) return true;
        Transaction transaction = new Transaction();
        transaction.setOrigin(origin);
        Account destination;
        try {
            destination = accountsService.getAccount(accountNumber);
            transaction.setDestination(destination);
            return printFundTransferScreen2(transaction);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean printFundTransferScreen2(Transaction transaction) throws Exception {
        clearScreen();
        System.out.print(
                "Please enter transfer amount and \n" +
                "press enter to continue or \n" +
                "press cancel (Esc) to go back to Transaction: ");
        String amount = scanner.nextLine();
        if("".equals(amount)) return false;
        try {
            Long transferAmount = Long.parseLong(amount);
            transaction.setAmount(transferAmount);
            return printFundTransferScreen3(transaction );
        } catch (NumberFormatException e) {
            throw new Exception("Invalid amount");
        }
    }

    private boolean printFundTransferScreen3(Transaction transaction) throws Exception {
        clearScreen();
        System.out.print(
                "Please enter reference number (Optional) and \n" +
                "press enter to continue or \n" +
                "press cancel (Esc) to go back to Transaction: ");
        String referenceNumber = scanner.nextLine();
        if("ESC".equals(referenceNumber)) return false;
        validateReferenceNumber(referenceNumber);
        transaction.setReferenceNumber(referenceNumber);
        return printFundTransferScreen4(transaction);
    }

    private boolean printFundTransferScreen4(Transaction transaction) {
        clearScreen();
        System.out.print(
                "Transfer Confirmation\n" +
                "Destination Account : " + transaction.getDestination().getAccountNumber() + "\n" +
                "Transfer Amount     : $" + transaction.getAmount() + "\n" +
                "Reference Number    : " + transaction.getReferenceNumber() + "\n" +
                "\n" +
                "1. Confirm Trx\n" +
                "2. Cancel Trx\n" +
                "Choose option[2]:");

        boolean shouldContinue;
        Integer option;
        do {
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    return performFundTransfer(transaction);
                case 2:
                    return true;
                default:
                    shouldContinue = option != 1 && option != 2;
                    break;
            }
        } while (shouldContinue);

        return shouldContinue;
    }

    private boolean performFundTransfer(Transaction transaction) {
        try {
            transactionService.performTransaction(transaction);
            return printFundTransferSummaryScreen(transaction);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Amount");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static void validateReferenceNumber(String referenceNumber) throws Exception {
        try {
            if(!"".equals(referenceNumber)) {
                Long.parseLong(referenceNumber);
            }
        } catch (NumberFormatException e) {
            throw new Exception("Invalid Reference Number");
        }
    }

    private static boolean printFundTransferSummaryScreen(Transaction transaction) {
        System.out.print(
                "Fund Transfer Summary\n" +
                "Destination Account : " + transaction.getDestination().getAccountNumber() +"\n" +
                "Transfer Amount     : $" + transaction.getAmount() +"\n" +
                "Reference Number    : " + transaction.getReferenceNumber() +"\n" +
                "Balance             : $" + transaction.getOrigin().getBalance() + "\n" +
                "\n" +
                "1. Transaction\n" +
                "2. Exit\n" +
                "Choose option[2]:");
        Integer option;
        boolean shouldContinue;
        do {
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    shouldContinue = option != 1 && option != 2;
                    break;
            }
        } while (shouldContinue);
        return shouldContinue;
    }

    private boolean printWithdrawMenu(Account account) {
        clearScreen();
        boolean shouldContinue = false;
        do{
            shouldContinue = processWithdrawOption(account);
        }while (shouldContinue);
        return true; //To get back to previous menu
    }

    private boolean processWithdrawOption(Account account) {
        System.out.print(
                "1. $10\n" +
                "2. $50\n" +
                "3. $100\n" +
                "4. Other\n" +
                "5. Back\n" +
                "Please choose option[5]:");
        int option = scanner.nextInt();
        Transaction transaction;
        try {
            switch (option) {
                case 1:
                    transaction = new Transaction(Transaction.TransactionType.WITHDRAW, account, 10L);
                    transactionService.performTransaction(transaction);
                    return printWithDrawSummaryScreen(transaction);
                case 2:
                    transaction = new Transaction(Transaction.TransactionType.WITHDRAW, account, 50L);
                    transactionService.performTransaction(transaction);
                    return printWithDrawSummaryScreen(transaction);
                case 3:
                    transaction = new Transaction(Transaction.TransactionType.WITHDRAW, account, 100L);
                    transactionService.performTransaction(transaction);
                    return printWithDrawSummaryScreen(transaction);
                case 4:
                    return printOtherWithdrawScreen(account);
                case 5: //Get back to previous menu
                    return false;
                default:
                    return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private boolean printWithDrawSummaryScreen(Transaction transaction) {
        System.out.print(
                "Date : "+ transaction.getTransactionDate() + "\n" +
                "Withdraw : $" + transaction.getAmount() + "\n" +
                "Balance : $" + transaction.getOrigin().getBalance() + "\n" +
                "\n" +
                "1. Transaction \n" +
                "2. Exit\n" +
                "Choose option[2]:");
        int option;
        boolean shouldContinue;
        do {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    return false;
                case 2:
                    return true;
                default:
                    shouldContinue = option != 1 && option != 2;
                    break;
            }
        } while (shouldContinue);
        return true;
    }

    private boolean printOtherWithdrawScreen(Account account) {
        clearScreen();
        System.out.println("Other Withdraw ");
        System.out.print("Enter amount to withdraw: ");
        String possibleNumber = scanner.next();
        Long amount = 0L;
        try{
            amount = Long.parseLong(possibleNumber);
            if(amount % 10 != 0)
                throw new Exception("Invalid Amount ");
            Transaction transaction = new Transaction(Transaction.TransactionType.WITHDRAW, account, amount);
            transactionService.performTransaction(transaction); //May throw an exception
            return printWithDrawSummaryScreen(transaction);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Amount ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private Account validateAccount(String account, String pin) {
        return accountsService.getAndValidateAccount(account, pin);
    }

    public void clearScreen() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            System.out.print("\033[H\033[2J");
        }
    }

}
