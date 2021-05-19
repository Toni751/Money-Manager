package dk.tonigr.moneymanager.ui.fragments.expenses;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.Expense;
import dk.tonigr.moneymanager.viewmodels.AccountViewModel;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;
import dk.tonigr.moneymanager.viewmodels.ExpenseViewModel;

public class AddExpenseFragment extends Fragment {
    private EditText name;
    private EditText amount;
    private EditText date;

    private TextView nameError;
    private TextView amountError;
    private TextView dateError;

    private List<Account> accounts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        ExpenseViewModel viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        // accounts = accountViewModel.getAccounts().getValue();
        // categories = categoryViewModel.getCategories().getValue();

        Spinner accountSpinner = view.findViewById(R.id.addExpenseAccountSpinner);
        Spinner categorySpinner = view.findViewById(R.id.addExpenseCategorySpinner);

        String[] accountNames = convertAccountsToStrings(accounts);

        ArrayAdapter<String> accountSpinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, accountNames);
        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(accountSpinnerAdapter);

        String[] categoryNames = convertCategoriesToStrings(categories);
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, categoryNames);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        accountViewModel.getAccounts().observe(getViewLifecycleOwner(), a -> {
            accounts = a;
            String[] temp = convertAccountsToStrings(accounts);

            ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, temp);
            newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            accountSpinner.setAdapter(newSpinnerAdapter);
        });
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), c -> {
            categories = c;
            String[] temp = convertCategoriesToStrings(categories);

            ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, temp);
            newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(newSpinnerAdapter);
        });

        name = view.findViewById(R.id.addExpenseName);
        amount = view.findViewById(R.id.addExpenseAmount);
        date = view.findViewById(R.id.addExpenseDate);

        nameError = view.findViewById(R.id.addExpenseNameError);
        amountError = view.findViewById(R.id.addExpenseAmountError);
        dateError = view.findViewById(R.id.addExpenseDateError);

        nameError.setVisibility(View.GONE);
        amountError.setVisibility(View.GONE);
        dateError.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);

        final String[] dateString = {""};
        date.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> {
                month += 1;
                dateString[0] = year + "-" + convertDayMonthToString(month) + "-" + convertDayMonthToString(day);
                date.setText(convertDayMonthToString(day) + "/" + convertDayMonthToString(month) + "/" + year);

                TimePickerDialog timeDialog = new TimePickerDialog(view.getContext(), (timePicker, hour, minute) -> {
                    dateString[0] += " "  + convertDayMonthToString(hour) + ":" + convertDayMonthToString(minute);
                    date.setText(date.getText() + " "  + convertDayMonthToString(hour) + ":" + convertDayMonthToString(minute));
                }, currentHour, currentMinute, true);
                timeDialog.show();
            }, currentYear, currentMonth, currentDay);
            dialog.show();
        });

        CheckBox checkBox = view.findViewById(R.id.addExpenseIsRecurring);

        Button button = view.findViewById(R.id.submitExpenseButton);
        button.setOnClickListener(v -> {
            if(!(validateForm())){
                return;
            }
            String selectedAccount = accountSpinner.getSelectedItem().toString();
            int selectedAccountId = 0;
            double selectedAccountAmount = 0;
            for (Account account : accounts) {
                if(account.getName().equals(selectedAccount)){
                    selectedAccountId = account.getAccount_id();
                    selectedAccountAmount = account.getAmount();
                }
            }
            String selectedCategory = categorySpinner.getSelectedItem().toString();
            int selectedCategoryId = 0;
            for (Category category : categories) {
                if(category.getName().equals(selectedCategory)) selectedCategoryId = category.getCategory_id();
            }
            Expense expense = new Expense(selectedAccountId, selectedCategoryId, name.getText().toString(), dateString[0], Double.parseDouble(amount.getText().toString()),checkBox.isChecked());
            viewModel.addExpense(expense);
            double newAccountAmount = selectedAccountAmount - Double.parseDouble(amount.getText().toString());
            accountViewModel.updateAccountAmount(selectedAccountId, newAccountAmount);
            Navigation.findNavController(view).navigate(R.id.action_addExpenseFragment_to_overviewFragment);
        });

        return view;
    }

    private String[] convertAccountsToStrings(List<Account> accounts){
        String[] accountNames = new String[accounts.size()];
        for (int i = 0; i < accountNames.length; i++) {
            accountNames[i] = accounts.get(i).getName();
        }

        return accountNames;
    }

    private String[] convertCategoriesToStrings(List<Category> categories){
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categoryNames.length; i++) {
            categoryNames[i] = categories.get(i).getName();
        }

        return categoryNames;
    }

    private String convertDayMonthToString(int value){
        return value < 10 ?  "0" + value : "" + value;
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (name.getText().toString().equals("")) {
            nameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            nameError.setVisibility(View.GONE);
        }

        if (amount.getText().toString().equals("")) {
            amountError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            amountError.setVisibility(View.GONE);
        }

        if (date.getText().toString().equals("")) {
            dateError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            dateError.setVisibility(View.GONE);
        }

        return isValid;
    }
}