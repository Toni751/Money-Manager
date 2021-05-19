package dk.tonigr.moneymanager.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.data.repositories.GoalRepository;
import dk.tonigr.moneymanager.models.db.Goal;

public class GoalViewModel extends AndroidViewModel {

    private GoalRepository goalRepository;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        goalRepository = GoalRepository.getInstance(application);
    }

    public void addGoal(Goal goal){
        goalRepository.addGoal(goal);
    }

    public LiveData<List<Goal>> getGoals() {
        return goalRepository.getGoals();
    }

    public void updateGoal(Goal goal){
        goalRepository.updateGoal(goal);
    }

    public void deleteGoal(Goal goal){
        goalRepository.deleteGoal(goal);
    }
}
