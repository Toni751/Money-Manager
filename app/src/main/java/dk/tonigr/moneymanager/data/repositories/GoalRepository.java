package dk.tonigr.moneymanager.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.tonigr.moneymanager.data.dao.GoalDAO;
import dk.tonigr.moneymanager.data.dao.MoneyManagerDatabase;
import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.models.db.Goal;

public class GoalRepository {
    private static GoalRepository instance;
    private GoalDAO goalDAO;
    private ExecutorService executorService;
    private LiveData<List<Goal>> allGoals;

    private GoalRepository(Application application){
        goalDAO = MoneyManagerDatabase.getInstance(application).goalDAO();
        allGoals = goalDAO.getAllGoals();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static GoalRepository getInstance(Application application){
        if(instance == null){
            instance = new GoalRepository(application);
        }

        return instance;
    }

    public void addGoal(Goal goal) {
        executorService.execute(() -> goalDAO.insert(goal));
    }

    public LiveData<List<Goal>> getGoals() {
        allGoals = goalDAO.getAllGoals();
        return allGoals;
    }

    public void updateGoal(Goal goal) {
        executorService.execute(() -> goalDAO.update(goal));
    }

    public void deleteGoal(Goal goal) {
        executorService.execute(() -> goalDAO.delete(goal));
    }
}
