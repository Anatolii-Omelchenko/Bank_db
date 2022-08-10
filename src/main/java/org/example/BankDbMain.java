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
                    int numberForDeposit = 663422784;
                    String depositCurrency = "USD";
                    float sum = 233;
                    DataBaseManager.deposit(numberForDeposit, depositCurrency, sum);
                } else if (choose.equals("5")) {
                    int fromNumber = 663422784;
                    int toNumber = 123422233;
                    float sum = 400;
                    DataBaseManager.transferMoney(fromNumber, toNumber, sum);
                } else if (choose.equals("6")) {
                    int fromNumber = 663422784;
                    float sum = 100;
                    String withdrawalCurrency = "USD";
                    DataBaseManager.withdrawal(fromNumber, sum, withdrawalCurrency);
                } else if (choose.equals("7")) {
                    DataBaseManager.showClientReportById(1);
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
