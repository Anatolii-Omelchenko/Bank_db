package org.example;

import javax.persistence.*;
import java.util.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String currency;

    @Column(unique = true)
    private int number;

    private double money;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<MyTransaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String currency, int number, Client client) {
        this.currency = currency;
        this.number = number;
        this.client = client;
    }

    public void addTransaction(MyTransaction transaction){
        transactions.add(transaction);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && number == account.number && Double.compare(account.money, money) == 0 && Objects.equals(currency, account.currency) && Objects.equals(client, account.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currency, number, money, client);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", number=" + number +
                ", money=" + money + " client: " + client.getName();
    }
}
