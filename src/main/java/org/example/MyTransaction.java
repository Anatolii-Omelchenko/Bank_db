package org.example;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "my_transactions")
public class MyTransaction {

    @Id
    private int id;
    private Double sum;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private Account to_account;

    public MyTransaction() {
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getTo_account() {
        return to_account;
    }

    public void setTo_account(Account to_account) {
        this.to_account = to_account;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTransaction that = (MyTransaction) o;
        return id == that.id && Objects.equals(sum, that.sum) && Objects.equals(account, that.account) && Objects.equals(to_account, that.to_account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, account, to_account);
    }

    @Override
    public String toString() {
        return "MyTransaction{" +
                "id=" + id +
                ", sum=" + sum +
                ", account=" + account +
                ", to_account=" + to_account +
                '}';
    }
}
