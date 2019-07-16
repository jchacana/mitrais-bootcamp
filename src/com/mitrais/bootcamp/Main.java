package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.Account;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int ACCT_LENGHT = 6;
    private static final int PIN_LENGHT = 6;

    private static List<Account> accounts = new ArrayList<>();
    private static Scanner scanner;

    public static void main(String[] args) {

        Account johnDoe = new Account("John Doe", "012108", 100L, "112233");
        Account janeDoe = new Account("Jane Doe", "932012", 30L, "112244");

        accounts.add(johnDoe);
        accounts.add(janeDoe);

        boolean show;
        do {
            show = showWelcomeScreen();
        } while (show);
    }

    private static boolean showWelcomeScreen() {
        clearScreen();
        System.out.print("Enter Account Number: ");
        scanner = new Scanner(System.in);
        String accountNumber = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

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

        Account account = searchAccount(accountNumber, pin);
        if(account == null) {
            System.out.println("Invalid Account Number/PIN");
            return true;
        }

        printTransactionScreen(account);

        return false;
    }

    private static void printTransactionScreen(Account account) {
        boolean shouldContinue = false;
        do {
            shouldContinue = printMenu(account);
        } while (shouldContinue);
    }

    private static boolean printMenu(Account account) {
        clearScreen();
        System.out.print(
                "1. Withdraw\n" +
                "2. Fund Transfer\n" +
                "3. Exit\n" +
                "Please choose option[3]:");
        int option = scanner.nextInt();
        boolean shouldContinue = false;
        switch (option) {
            case 1:
                shouldContinue = printWithdrawMenu(account);
                break;
            case 2:
                printFundTransferScreen(account);
                break;
            case 3:
                return false;
            default:
                return true;
        }
        return shouldContinue;
    }

    private static void printFundTransferScreen(Account account) {
        clearScreen();

    }

    private static boolean printWithdrawMenu(Account account) {
        clearScreen();
        boolean shouldContinue = false;
        do{
            shouldContinue = processWithdrawOption(account);
        }while (shouldContinue);
        return true; //To get back to previous menu
    }

    private static boolean processWithdrawOption(Account account) {
        System.out.print(
                "1. $10\n" +
                "2. $50\n" +
                "3. $100\n" +
                "4. Other\n" +
                "5. Back\n" +
                "Please choose option[5]:");
        int option = scanner.nextInt();
        try {
            switch (option) {
                case 1:
                    account.withdraw(10L);
                    return printSummaryScreen(10L, account);
                case 2:
                    account.withdraw(50L);
                    return printSummaryScreen(50L, account);
                case 3:
                    account.withdraw(100L);
                    return printSummaryScreen(100L, account);
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

    private static boolean printSummaryScreen(Long amount, Account account) {
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        System.out.print(
                "Date : "+ formatter.format(now) + "\n" +
                "Withdraw : $" + amount + "\n" +
                "Balance : $" + account.getBalance() + "\n" +
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

    private static boolean printOtherWithdrawScreen(Account account) {
        clearScreen();
        System.out.println("Other Withdraw ");
        System.out.print("Enter amount to withdraw: ");
        String possibleNumber = scanner.next();
        Long amount = 0L;
        try{
            amount = Long.parseLong(possibleNumber);
            if(amount % 10 != 0)
                throw new Exception("Invalid Amount ");
            if(amount > 1000)
                throw new Exception("Maximum amount to withdraw is $1000 ");
            account.withdraw(amount); //May throw an exception
            return printSummaryScreen(amount, account);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Amount ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static Account searchAccount(String account, String pin) {
        for(Account account1: accounts) {
            if(account1.getAccountNumber().equals(account) && account1.getPin().equals(pin))
               return account1;
        }
        return null;
    }

    public static void clearScreen() {
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
