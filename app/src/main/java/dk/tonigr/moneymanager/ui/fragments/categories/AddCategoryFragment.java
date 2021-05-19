package dk.tonigr.moneymanager.ui.fragments.categories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;

public class AddCategoryFragment extends Fragment {
    private EditText name;
    private EditText monthlyGoal;

    public TextView nameError;
    public TextView monthlyGoalError;

    public AddCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        name = view.findViewById(R.id.addCategoryName);
        monthlyGoal = view.findViewById(R.id.addCategoryMonthlyGoal);

        nameError = view.findViewById(R.id.addCategoryNameError);
        monthlyGoalError = view.findViewById(R.id.addCategoryMonthlyGoalError);

        nameError.setVisibility(View.GONE);
        monthlyGoalError.setVisibility(View.GONE);

        Button button = view.findViewById(R.id.submitCategoryButton);
        button.setOnClickListener(v -> {
            if(!isValid()){
                return;
            }

            viewModel.addCategory(new Category(name.getText().toString(), Double.parseDouble(monthlyGoal.getText().toString())));
            Navigation.findNavController(view).navigate(R.id.action_addCategoryFragment_to_categoriesFragment);
        });

        return view;
    }

    private boolean isValid(){
        boolean isValid = true;

        if (name.getText().toString().equals("")) {
            nameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            nameError.setVisibility(View.GONE);
        }

        if (monthlyGoal.getText().toString().equals("")) {
            monthlyGoalError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            monthlyGoalError.setVisibility(View.GONE);
        }

        return isValid;
    }
}