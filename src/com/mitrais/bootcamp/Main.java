package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.Account;

import java.io.IOException;
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
        int i = scanner.nextInt();
        switch (i) {
            case 1:
                printWithdrawScreen(account);
                break;
            case 2:
                printFundTransferScreen(account);
                break;
            case 3:
                return false;
            default:
                return true;
        }
        return false;
    }

    private static void printFundTransferScreen(Account account) {
        clearScreen();
    }

    private static void printWithdrawScreen(Account account) {
        clearScreen();
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
