package dk.tonigr.moneymanager.ui.fragments.goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.data.adapters.AccountAdapter;
import dk.tonigr.moneymanager.data.adapters.GoalCardAdapter;
import dk.tonigr.moneymanager.models.db.Goal;
import dk.tonigr.moneymanager.viewmodels.AccountViewModel;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class GoalsFragment extends Fragment {

    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);
        GoalViewModel viewModel = new ViewModelProvider(this).get(GoalViewModel.class);

        RecyclerView goalsRV = view.findViewById(R.id.goalsRecyclerView);
        goalsRV.hasFixedSize();
        goalsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        GoalCardAdapter adapter = new GoalCardAdapter(viewModel, goal -> {
            GoalsFragmentDirections.ActionGoalsFragmentToEditGoalFragment action = GoalsFragmentDirections.actionGoalsFragmentToEditGoalFragment(goal);
            Navigation.findNavController(view).navigate(action);
        });

        goalsRV.setAdapter(adapter);
        viewModel.getGoals().observe(getViewLifecycleOwner(), adapter::setGoals);

        Button button = view.findViewById(R.id.addGoalButton);
        button.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_addGoalFragment));
        return view;
    }
}