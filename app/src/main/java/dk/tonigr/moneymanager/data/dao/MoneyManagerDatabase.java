package dk.tonigr.moneymanager.data.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.Expense;
import dk.tonigr.moneymanager.models.db.Goal;

@Database(entities = {Account.class, Category.class, Expense.class, Goal.class}, version = 2)
public abstract class MoneyManagerDatabase extends RoomDatabase {

    private static MoneyManagerDatabase instance;

    public abstract AccountDAO accountDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract ExpenseDAO expenseDAO();
    public abstract GoalDAO goalDAO();

    public static synchronized MoneyManagerDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoneyManagerDatabase.class, "money_manager_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
