package dk.tonigr.moneymanager.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.tonigr.moneymanager.data.dao.AccountDAO;
import dk.tonigr.moneymanager.data.dao.MoneyManagerDatabase;
import dk.tonigr.moneymanager.models.db.Account;

public class AccountRepository {
    private static AccountRepository instance;
    private AccountDAO accountDAO;
    private ExecutorService executorService;
    private LiveData<List<Account>> allAccounts;

    private AccountRepository(Application application) {
        accountDAO = MoneyManagerDatabase.getInstance(application).accountDAO();
        allAccounts = accountDAO.getAllAccounts();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static AccountRepository getInstance(Application application){
        if(instance == null) {
            instance = new AccountRepository(application);
        }

        return instance;
    }

    public void addAccount(Account account) {
        executorService.execute(() -> accountDAO.insert(account));
    }

    public LiveData<List<Account>> getAccounts() {
//        List<Account> accounts = new ArrayList<>();
//        accounts.add(new Account(1, "Sydbank card", "Credit card", 20000, 7000));
//        accounts.add(new Account(2, "Revolut card", "Debit card", 2000, 300));
//        accounts.add(new Account(3, "Cash hidden under the bed", "Cash", 500, 200));
//        return accounts;
//        Log.i("please", allAccounts.getValue().toString());
        Log.i("please", "Repository getting all accounts");
        allAccounts = accountDAO.getAllAccounts();
        return allAccounts;
    }

    public void updateAccount(Account account) {
        executorService.execute(() -> accountDAO.update(account));
    }

    public void deleteAccount(Account account) {
        executorService.execute(() -> accountDAO.delete(account));
    }

    public void updateAccountAmount(int account_id, double new_amount) {
        executorService.execute(() -> accountDAO.updateAccountAmount(account_id, new_amount));
    }
}
