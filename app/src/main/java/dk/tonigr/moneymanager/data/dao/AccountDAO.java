package dk.tonigr.moneymanager.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.tonigr.moneymanager.models.db.Account;

@Dao
public interface AccountDAO {
    @Insert
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    LiveData<List<Account>> getAllAccounts();

    @Query("UPDATE accounts SET amount = :new_amount WHERE account_id = :acc_id")
    void updateAccountAmount(int acc_id, double new_amount);

}
