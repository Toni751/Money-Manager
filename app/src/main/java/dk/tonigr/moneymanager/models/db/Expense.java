package dk.tonigr.moneymanager.models.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "expenses", foreignKeys = {
        @ForeignKey(entity = Account.class, parentColumns = "account_id", childColumns = "account_id"),
        @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id")
})
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int expense_id;

    private int account_id;
    private int category_id;

    private String name;
    private String dateTime;
    private double amount;
    private boolean is_recurring;

    public Expense(int account_id, int category_id, String name, String dateTime, double amount, boolean is_recurring) {
        this.account_id = account_id;
        this.category_id = category_id;
        this.name = name;
        this.dateTime = dateTime;
        this.amount = amount;
        this.is_recurring = is_recurring;
    }

    @Ignore
    public Expense(int expense_id, int account_id, int category_id, String name, String dateTime, double amount, boolean is_recurring) {
        this.expense_id = expense_id;
        this.account_id = account_id;
        this.category_id = category_id;
        this.name = name;
        this.dateTime = dateTime;
        this.amount = amount;
        this.is_recurring = is_recurring;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isIs_recurring() {
        return is_recurring;
    }

    public void setIs_recurring(boolean is_recurring) {
        this.is_recurring = is_recurring;
    }
}
