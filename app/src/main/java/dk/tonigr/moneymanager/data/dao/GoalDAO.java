package dk.tonigr.moneymanager.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.tonigr.moneymanager.models.db.Goal;

@Dao
public interface GoalDAO {
    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);

    @Delete
    void delete(Goal goal);

    @Query("SELECT * FROM goals ORDER BY due_date ASC")
    LiveData<List<Goal>> getAllGoals();
}
