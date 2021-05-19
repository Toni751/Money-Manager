package dk.tonigr.moneymanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.tonigr.moneymanager.data.repositories.AccountRepository;
import dk.tonigr.moneymanager.models.db.Account;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = AccountRepository.getInstance(application);
    }

    public void addAccount(Account account){
        accountRepository.addAccount(account);
    }

    public void updateAccount(Account account){
        accountRepository.updateAccount(account);
    }

    public void deleteAccount(Account account){
        accountRepository.deleteAccount(account);
    }

    public LiveData<List<Account>> getAccounts(){
        return accountRepository.getAccounts();
    }

    public void updateAccountAmount(int account_id, double new_amount){
        accountRepository.updateAccountAmount(account_id, new_amount);
    }
}
