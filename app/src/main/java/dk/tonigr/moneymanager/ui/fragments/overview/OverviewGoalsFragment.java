package dk.tonigr.moneymanager.ui.fragments.overview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.core.annotations.Line;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.data.adapters.CardAdapter;
import dk.tonigr.moneymanager.data.adapters.GoalOverviewAdapter;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class OverviewGoalsFragment extends Fragment {

    public OverviewGoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview_goals, container, false);
        GoalViewModel viewModel = new ViewModelProvider(this).get(GoalViewModel.class);

        RecyclerView goalsRv = view.findViewById(R.id.goalsOverviewRecyclerView);
        goalsRv.hasFixedSize();
        goalsRv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        GoalOverviewAdapter adapter = new GoalOverviewAdapter(viewModel);
        goalsRv.setAdapter(adapter);
        viewModel.getGoals().observe(getViewLifecycleOwner(), adapter::setGoals);
        return view;
    }
}