package dk.tonigr.moneymanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.tonigr.moneymanager.data.repositories.ExpenseRepository;
import dk.tonigr.moneymanager.models.db.Expense;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository expenseRepository;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = ExpenseRepository.getInstance(application);
    }

    public void addExpense(Expense expense){
        expenseRepository.addExpense(expense);
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return expenseRepository.getAllExpenses();
    }

    public void updateExpense(Expense expense){
        expenseRepository.updateExpense(expense);
    }

    public void deleteExpense(Expense expense){
        expenseRepository.deleteExpense(expense);
    }
}
