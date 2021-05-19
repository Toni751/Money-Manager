package dk.tonigr.moneymanager.models.db;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.VersionedParcelize;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "goals")
public class Goal implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int goal_id;
    private String name;
    private double target_amount;
    private double current_amount;
    private String start_date;
    private String due_date;

    public Goal(String name, double target_amount, double current_amount, String start_date, String due_date) {
        this.name = name;
        this.target_amount = target_amount;
        this.current_amount = current_amount;
        this.start_date = start_date;
        this.due_date = due_date;
    }

    @Ignore
    public Goal(int goal_id, String name, double target_amount, double current_amount, String start_date, String due_date) {
        this.goal_id = goal_id;
        this.name = name;
        this.target_amount = target_amount;
        this.current_amount = current_amount;
        this.start_date = start_date;
        this.due_date = due_date;
    }

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTarget_amount() {
        return target_amount;
    }

    public void setTarget_amount(double target_amount) {
        this.target_amount = target_amount;
    }

    public double getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(double current_amount) {
        this.current_amount = current_amount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
