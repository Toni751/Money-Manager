package dk.tonigr.moneymanager.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.tonigr.moneymanager.data.dao.ExpenseDAO;
import dk.tonigr.moneymanager.data.dao.MoneyManagerDatabase;
import dk.tonigr.moneymanager.models.db.Expense;

public class ExpenseRepository {
    private static ExpenseRepository instance;
    private ExpenseDAO expenseDAO;
    private ExecutorService executorService;
    private LiveData<List<Expense>> allExpenses;

    private ExpenseRepository(Application application){
        expenseDAO = MoneyManagerDatabase.getInstance(application).expenseDAO();
        allExpenses = expenseDAO.getAllExpenses();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static ExpenseRepository getInstance(Application application){
        if(instance == null){
            instance = new ExpenseRepository(application);
        }

        return instance;
    }

    public void addExpense(Expense expense){
        executorService.execute(() -> expenseDAO.insert(expense));
    }

    public LiveData<List<Expense>> getAllExpenses(){
        allExpenses = expenseDAO.getAllExpenses();
        return allExpenses;
    }

    public void updateExpense(Expense expense){
        executorService.execute(() -> expenseDAO.update(expense));
    }

    public void deleteExpense(Expense expense){
        executorService.execute(() -> expenseDAO.delete(expense));
    }
}
