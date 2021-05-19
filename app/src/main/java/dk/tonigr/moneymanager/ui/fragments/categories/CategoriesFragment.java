package dk.tonigr.moneymanager.ui.fragments.categories;

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
import dk.tonigr.moneymanager.data.adapters.CategoryCardAdapter;
import dk.tonigr.moneymanager.data.adapters.GoalCardAdapter;
import dk.tonigr.moneymanager.ui.fragments.goals.GoalsFragmentDirections;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class CategoriesFragment extends Fragment {

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        RecyclerView categoriesRV = view.findViewById(R.id.categoryCardRecyclerView);
        categoriesRV.hasFixedSize();
        categoriesRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        CategoryCardAdapter adapter = new CategoryCardAdapter(viewModel, category -> {
            CategoriesFragmentDirections.ActionCategoriesFragmentToEditCategoryFragment action = CategoriesFragmentDirections.actionCategoriesFragmentToEditCategoryFragment(category);
            Navigation.findNavController(view).navigate(action);
        });

        categoriesRV.setAdapter(adapter);
        viewModel.getCategories().observe(getViewLifecycleOwner(), adapter::setCategories);

        Button button = view.findViewById(R.id.addCategoryButton);
        button.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_categoriesFragment_to_addCategoryFragment));
        return view;
    }
}