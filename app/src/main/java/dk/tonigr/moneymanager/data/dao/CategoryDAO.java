package dk.tonigr.moneymanager.data.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;

@Dao
public interface CategoryDAO {
    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories ORDER BY name ASC")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories ORDER BY name ASC")
    LiveData<List<CategoryWithExpenses>> getAllCategoriesWithExpenses();

    // @Query("SELECT DISTINCT c.* FROM categories c LEFT JOIN expenses e ON c.category_id = e.category_id WHERE e.dateTime > :month ORDER BY name ASC")
    // @Query("SELECT e.* FROM expenses e JOIN categories c ON c.category_id = e.category_id WHERE e.dateTime > :month ORDER BY name ASC")
    @Query("SELECT * FROM categories c JOIN expenses e ON c.category_id = e.category_id WHERE e.dateTime LIKE :month ORDER BY c.name ASC")
    LiveData<List<CategoryWithExpenses>> getAllCategoriesWithExpensesForMonth(String month);
}
