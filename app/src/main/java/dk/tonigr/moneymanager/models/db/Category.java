package dk.tonigr.moneymanager.models.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "categories")
public class Category implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int category_id;
    private String name;
    private double monthly_goal;

    public Category(String name, double monthly_goal) {
        this.name = name;
        this.monthly_goal = monthly_goal;
    }

    @Ignore
    public Category(int category_id, String name, double monthly_goal) {
        this.category_id = category_id;
        this.name = name;
        this.monthly_goal = monthly_goal;
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

    public double getMonthly_goal() {
        return monthly_goal;
    }

    public void setMonthly_goal(double monthly_goal) {
        this.monthly_goal = monthly_goal;
    }
}
