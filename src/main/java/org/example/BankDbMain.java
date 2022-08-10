package org.example;

import java.util.Scanner;

public class BankDbMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            DataBaseManager.initDB();
            while (true) {

                System.out.println("1: view all clients");
                System.out.println("2: view all accounts");
                System.out.println("3: view all transactions\n");

                System.out.println("4: make deposit");
                System.out.println("5: make transfer");
                System.out.println("6: make withdrawal");
                System.out.println("7: sum of money by client id");

                System.out.print("-> ");

                String choose = scanner.nextLine();

                if (choose.equals("1")) {
                    DataBaseManager.viewAllClients();
                } else if (choose.equals("2")) {
                    DataBaseManager.viewAllAcc();
                } else if (choose.equals("3")) {
                    DataBaseManager.viewAllTransactions();
                } else if (choose.equals("4")) {
                    DataBaseManager.deposit(663422784, "USD", 233);
                } else if (choose.equals("5")) {
                    DataBaseManager.transferMoney(663422784,123422233,400);
                } else if (choose.equals("6")) {
                    DataBaseManager.withdrawal(663422784, 100, "USD");
                } else if (choose.equals("7")) {
                    DataBaseManager.getSumOfClientMoney(1);
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            DataBaseManager.closeDataBase();
        }


    }
}
