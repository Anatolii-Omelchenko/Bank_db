package org.example;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ExchangeRate {

    @Id
    private int id;
    private String euroRate;
    private String dollarRate;

    public ExchangeRate(String euroRate, String dollarRate) {
        this.euroRate = euroRate;
        this.dollarRate = dollarRate;
    }

    public ExchangeRate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEuroRate() {
        return euroRate;
    }

    public void setEuroRate(String euroRate) {
        this.euroRate = euroRate;
    }

    public String getDollarRate() {
        return dollarRate;
    }

    public void setDollarRate(String dollarRate) {
        this.dollarRate = dollarRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return id == that.id && Objects.equals(euroRate, that.euroRate) && Objects.equals(dollarRate, that.dollarRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, euroRate, dollarRate);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", euroRate='" + euroRate + '\'' +
                ", dollarRate='" + dollarRate + '\'' +
                '}';
    }
}