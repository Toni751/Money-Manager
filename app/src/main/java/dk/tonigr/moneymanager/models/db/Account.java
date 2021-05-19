package dk.tonigr.moneymanager.models.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int account_id;
    private String name;
    private String type;
    private double amount;
    private double monthly_income;

    public Account(String name, String type, double amount, double monthly_income) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.monthly_income = monthly_income;
    }

    @Ignore
    public Account(int account_id, String name, String type, double amount, double monthly_income) {
        this.account_id = account_id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.monthly_income = monthly_income;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(double monthly_income) {
        this.monthly_income = monthly_income;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", monthly_income=" + monthly_income +
                '}';
    }
}
