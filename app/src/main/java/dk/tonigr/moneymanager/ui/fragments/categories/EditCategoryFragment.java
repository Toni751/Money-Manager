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

public class EditCategoryFragment extends Fragment {
    private EditText name;
    private EditText monthlyGoal;

    public TextView nameError;
    public TextView monthlyGoalError;

    public EditCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_category, container, false);
        Category category = EditCategoryFragmentArgs.fromBundle(getArguments()).getCategory();
        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        name = view.findViewById(R.id.editCategoryName);
        monthlyGoal = view.findViewById(R.id.editCategoryMonthlyGoal);

        name.setText(category.getName());
        monthlyGoal.setText(String.valueOf(category.getMonthly_goal()));

        nameError = view.findViewById(R.id.editCategoryNameError);
        monthlyGoalError = view.findViewById(R.id.editCategoryMonthlyGoalError);

        nameError.setVisibility(View.GONE);
        monthlyGoalError.setVisibility(View.GONE);

        Button editButton = view.findViewById(R.id.submitEditCategoryButton);
        editButton.setOnClickListener(v -> {
            if(!isValid()){
                return;
            }

            category.setName(name.getText().toString());
            category.setMonthly_goal(Double.parseDouble(monthlyGoal.getText().toString()));

            viewModel.updateCategory(category);
            Navigation.findNavController(view).navigate(R.id.action_editCategoryFragment_to_categoriesFragment);
        });

        Button deleteButton = view.findViewById(R.id.deleteCategoryButton);
        deleteButton.setOnClickListener(v -> {
            viewModel.deleteCategory(category);
            Navigation.findNavController(view).navigate(R.id.action_editCategoryFragment_to_categoriesFragment);
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