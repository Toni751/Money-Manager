package dk.tonigr.moneymanager.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.tonigr.moneymanager.models.db.Expense;

@Dao
public interface ExpenseDAO {
    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM expenses WHERE category_id = :category_id")
    LiveData<List<Expense>> getExpensesForCategory(int category_id);

    @Query("SELECT * FROM expenses ORDER BY dateTime DESC")
    LiveData<List<Expense>> getAllExpenses();
}
