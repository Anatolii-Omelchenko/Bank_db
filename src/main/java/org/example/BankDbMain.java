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
                System.out.println("4: view all orders\n");

                System.out.println("5: add client");
                System.out.println("6: add product");

                System.out.print("-> ");

                String choose = scanner.nextLine();

                if (choose.equals("1")) {
                    DataBaseManager.viewAllClients();
                } else if (choose.equals("2")) {
                    DataBaseManager.viewAllAcc();
                } else if (choose.equals("3")) {
                    DataBaseManager.deposit(663422784, "USD", 233);
                } else if (choose.equals("4")) {
                } else if (choose.equals("5")) {
                } else if (choose.equals("6")) {

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
