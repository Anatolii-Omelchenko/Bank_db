package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class DataBaseManager {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankJPA");
    static EntityManager em = emf.createEntityManager();
    static Scanner scanner = new Scanner(System.in);


    public static void initDB() {
        addClient("Anatoliy");
        addClient("Stephan");

        openAccount("USD", 111222333, 1);
        openAccount("UAH", 123422233, 1);
        openAccount("EUR", 663422233, 1);

        openAccount("USD", 111242233, 2);
        openAccount("UAH", 124372233, 2);
        openAccount("EUR", 663422784, 2);

        setCourse(40.88F, 39.23F);
    }

    private static void setCourse(float euro, float dollar) {
        ExchangeRate exchangeRate = new ExchangeRate(euro, dollar);
        performTransaction(() -> {
            em.merge(exchangeRate);
            return null;
        });
    }

    public static void showClientReportById(int clientID) {
        TypedQuery<Account> euroQuery = em.createQuery("SELECT x FROM Account x where x.client.id =: clientID AND x.currency =: currency", Account.class);
        euroQuery.setParameter("clientID", clientID);
        euroQuery.setParameter("currency", "EUR");

        List<Account> euroAccounts = euroQuery.getResultList();

        euroQuery.setParameter("currency", "USD");
        List<Account> usdAccounts = euroQuery.getResultList();

        euroQuery.setParameter("currency", "UAH");
        List<Account> uahAccounts = euroQuery.getResultList();

        TypedQuery<ExchangeRate> query = em.createQuery("SELECT x FROM ExchangeRate x", ExchangeRate.class);
        float euroCourse = query.getSingleResult().getEuroRate();
        float dollarCourse = query.getSingleResult().getDollarRate();

        double euro = 0;
        double usd = 0;
        double uah = 0;

        for (Account acc : euroAccounts) {
            euro += acc.getBalance();
        }
        for (Account acc : usdAccounts) {
            usd += acc.getBalance();
        }
        for (Account acc : uahAccounts) {
            uah += acc.getBalance();
        }

        double total = euroCourse * euro + dollarCourse * usd + uah;
        System.out.println("\nEURO: " + euro);
        System.out.println("USD: " + usd);
        System.out.println("UAH: " + uah);
        System.out.println("-----------------");
        System.out.println("Total: " + total + " UAH.\n");
    }

    public static void deposit(int number, String fromCurrency, float sum) {
        TypedQuery<Account> query = em.createQuery("SELECT x FROM Account x WHERE x.number=:number", Account.class);
        query.setParameter("number", number);
        Account account = query.getSingleResult();
        String toCurrency = account.getCurrency();

        float money = convertMoney(fromCurrency, toCurrency, sum);
        MyTransaction transaction = new MyTransaction(sum, null, account, "deposit", fromCurrency);
        account.addTransaction(transaction);

        performTransaction(() -> {
            account.setBalance(account.getBalance() + money);
            em.merge(account);
            return null;
        });
    }

    public static void withdrawal(int fromNumber, float sum, String toCurrency) {
        TypedQuery<Account> query = em.createQuery("SELECT x FROM Account x WHERE x.number=:number", Account.class);

        query.setParameter("number", fromNumber);
        Account fromAccount = query.getSingleResult();
        String fromCurrency = fromAccount.getCurrency();

        float money = convertMoney(fromCurrency, toCurrency, sum);

        if (fromAccount.getBalance() < money) {
            System.out.println("\n\tYou dont have enough money!\n");
            return;
        }
        MyTransaction transaction = new MyTransaction(sum, fromAccount, null, "withdrawal", toCurrency);
        fromAccount.addTransaction(transaction);

        performTransaction(() -> {
            fromAccount.setBalance(fromAccount.getBalance() - money);
            em.merge(fromAccount);
            return null;
        });
    }


    public static void transferMoney(int fromNumber, int toNumber, float sum) {
        TypedQuery<Account> query = em.createQuery("SELECT x FROM Account x WHERE x.number=:number", Account.class);

        query.setParameter("number", toNumber);
        Account toAccount = query.getSingleResult();
        String toCurrency = toAccount.getCurrency();

        query.setParameter("number", fromNumber);
        Account fromAccount = query.getSingleResult();
        String fromCurrency = fromAccount.getCurrency();

        if (fromAccount.getBalance() < sum) {
            System.out.println("\n\tYou dont have enough money!\n");
            return;
        }

        float money = convertMoney(fromCurrency, toCurrency, sum);
        MyTransaction transaction = new MyTransaction(sum, fromAccount, toAccount, "transfer", fromCurrency);
        toAccount.addTransaction(transaction);

        performTransaction(() -> {
            fromAccount.setBalance(fromAccount.getBalance() - sum);
            toAccount.setBalance(toAccount.getBalance() + money);
            em.merge(fromAccount);
            em.merge(toAccount);
            return null;
        });
    }

    private static float convertMoney(String fromCurrency, String toCurrency, float sum) {
        float money = sum;

        TypedQuery<ExchangeRate> query = em.createQuery("SELECT x FROM ExchangeRate x", ExchangeRate.class);
        float euro = query.getSingleResult().getEuroRate();
        float dollar = query.getSingleResult().getDollarRate();

        if (!fromCurrency.equals(toCurrency)) {
            if (fromCurrency.equals("UAH") && toCurrency.equals("USD")) {
                money = sum / dollar;
            } else if (fromCurrency.equals("UAH") && toCurrency.equals("EUR")) {
                money = sum / euro;
            } else if (fromCurrency.equals("USD") && toCurrency.equals("UAH")) {
                money = sum * dollar;
            } else if (fromCurrency.equals("EUR") && toCurrency.equals("UAH")) {
                money = sum * euro;
            } else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
                money = sum * (euro / dollar);
            } else if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
                money = sum * (dollar / euro);
            }
        }
        return money;
    }

    public static void addClient(String name) {
        final Client client = new Client(name);

        performTransaction(() -> {
            em.persist(client);
            return null;
        });
    }

    public static void openAccount(String currency, int number, int client_id) {
        Client client = em.getReference(Client.class, client_id);
        Account account = new Account(currency, number, client);
        client.addAccount(account);
        performTransaction(() -> {
            em.merge(client);
            return null;
        });
    }

    public static void viewAllClients() {
        TypedQuery<Client> query = em.createQuery("SELECT x FROM Client x", Client.class);
        List<Client> clientList = query.getResultList();
        for (Client cl : clientList) {
            System.out.println(cl);
        }
    }

    public static void viewAllAcc() {
        TypedQuery<Account> query = em.createQuery("SELECT x FROM Account x", Account.class);
        List<Account> clientList = query.getResultList();
        for (Account cl : clientList) {
            System.out.println(cl);
        }
    }

    public static void viewAllAccounts() {
        TypedQuery<Account> query = em.createQuery("SELECT x FROM Account x", Account.class);
        List<Account> clientList = query.getResultList();
        for (Account cl : clientList) {
            System.out.println(cl);
        }
    }

    public static void viewAllTransactions() {
        TypedQuery<MyTransaction> query = em.createQuery("SELECT x FROM MyTransaction x", MyTransaction.class);
        List<MyTransaction> clientList = query.getResultList();
        for (MyTransaction cl : clientList) {
            System.out.println(cl);
        }
    }

    private static <T> T performTransaction(Callable<T> action) {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        try {
            T result = action.call();
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void closeDataBase() {
        emf.close();
        em.close();
        scanner.close();
    }
}
