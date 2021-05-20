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
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.Expense;
import dk.tonigr.moneymanager.util.DateStringConverter;
import dk.tonigr.moneymanager.viewmodels.AccountViewModel;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;
import dk.tonigr.moneymanager.viewmodels.ExpenseViewModel;

public class EditExpenseFragment extends Fragment {
    private EditText name;
    private EditText amount;
    private EditText date;

    private TextView nameError;
    private TextView amountError;
    private TextView dateError;

    private List<Account> accounts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public EditExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_expense, container, false);
        Expense expense = EditExpenseFragmentArgs.fromBundle(getArguments()).getExpense();
        ExpenseViewModel viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        double initialExpenseAmount = expense.getAmount();

        Spinner accountSpinner = view.findViewById(R.id.editExpenseAccountSpinner);
        Spinner categorySpinner = view.findViewById(R.id.editExpenseCategorySpinner);

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

            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getAccount_id() == expense.getAccount_id()) {
                    accountSpinner.setSelection(i);
                    break;
                }
            }
        });
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), c -> {
            categories = c;
            String[] temp = convertCategoriesToStrings(categories);

            ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, temp);
            newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(newSpinnerAdapter);

            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getCategory_id() == expense.getCategory_id()) {
                    categorySpinner.setSelection(i);
                    break;
                }
            }
        });

        name = view.findViewById(R.id.editExpenseName);
        amount = view.findViewById(R.id.editExpenseAmount);
        date = view.findViewById(R.id.editExpenseDate);

        name.setText(expense.getName());
        amount.setText(String.valueOf(expense.getAmount()));
        String[] dateSplit = expense.getDateTime().split(" ");
        date.setText(DateStringConverter.convertDbDateToUiDate(dateSplit[0]) + " " + dateSplit[1]);

        nameError = view.findViewById(R.id.editExpenseNameError);
        amountError = view.findViewById(R.id.editExpenseAmountError);
        dateError = view.findViewById(R.id.editExpenseDateError);

        nameError.setVisibility(View.GONE);
        amountError.setVisibility(View.GONE);
        dateError.setVisibility(View.GONE);

        final String[] dateString = {expense.getDateTime()};
        date.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> {
                month += 1;
                dateString[0] = year + "-" + convertDayMonthToString(month) + "-" + convertDayMonthToString(day);
                date.setText(convertDayMonthToString(day) + "/" + convertDayMonthToString(month) + "/" + year);

                TimePickerDialog timeDialog = new TimePickerDialog(view.getContext(), (timePicker, hour, minute) -> {
                    dateString[0] += " " + convertDayMonthToString(hour) + ":" + convertDayMonthToString(minute);
                    date.setText(date.getText() + " " + convertDayMonthToString(hour) + ":" + convertDayMonthToString(minute));
                }, Integer.parseInt(dateSplit[1].split(":")[0]), Integer.parseInt(dateSplit[1].split(":")[1]), true);
                timeDialog.show();
            }, Integer.parseInt(dateSplit[0].split("-")[0]), Integer.parseInt(dateSplit[0].split("-")[1]), Integer.parseInt(dateSplit[0].split("-")[1]));
            dialog.show();
        });

        CheckBox checkBox = view.findViewById(R.id.editExpenseIsRecurring);
        checkBox.setChecked(expense.isIs_recurring());

        Button editButton = view.findViewById(R.id.submitEditExpenseButton);
        editButton.setOnClickListener(v -> {
            if (!(validateForm())) {
                return;
            }
            String selectedAccount = accountSpinner.getSelectedItem().toString();
            int selectedAccountId = 0;
            double selectedAccountAmount = 0;
            for (Account account : accounts) {
                if (account.getName().equals(selectedAccount)) {
                    selectedAccountId = account.getAccount_id();
                    selectedAccountAmount = account.getAmount();
                }
            }
            String selectedCategory = categorySpinner.getSelectedItem().toString();
            int selectedCategoryId = 0;
            for (Category category : categories) {
                if (category.getName().equals(selectedCategory))
                    selectedCategoryId = category.getCategory_id();
            }
            // Expense expense = new Expense(selectedAccountId, selectedCategoryId, name.getText().toString(), dateString[0], Double.parseDouble(amount.getText().toString()),checkBox.isChecked());
            double newExpenseAmount = Double.parseDouble(amount.getText().toString());

            expense.setAccount_id(selectedAccountId);
            expense.setCategory_id(selectedCategoryId);
            expense.setName(name.getText().toString());
            expense.setDateTime(dateString[0]);
            expense.setAmount(newExpenseAmount);
            expense.setIs_recurring(checkBox.isChecked());

            viewModel.updateExpense(expense);
            if (newExpenseAmount != initialExpenseAmount) {
                double newAccountAmount = selectedAccountAmount + initialExpenseAmount - newExpenseAmount;
                accountViewModel.updateAccountAmount(selectedAccountId, newAccountAmount);
            }
            Navigation.findNavController(view).navigate(R.id.action_editExpenseFragment_to_transactionsFragment);
        });

        Button deleteButton = view.findViewById(R.id.deleteExpenseButton);
        deleteButton.setOnClickListener(v -> {
            String selectedAccount = accountSpinner.getSelectedItem().toString();
            int selectedAccountId = 0;
            double selectedAccountAmount = 0;
            for (Account account : accounts) {
                if (account.getName().equals(selectedAccount)) {
                    selectedAccountId = account.getAccount_id();
                    selectedAccountAmount = account.getAmount();
                }
            }

            viewModel.deleteExpense(expense);
            double newAccountAmount = selectedAccountAmount + initialExpenseAmount;
            accountViewModel.updateAccountAmount(selectedAccountId, newAccountAmount);

            Navigation.findNavController(view).navigate(R.id.action_editExpenseFragment_to_transactionsFragment);
        });

        return view;
    }

    private String[] convertAccountsToStrings(List<Account> accounts) {
        String[] accountNames = new String[accounts.size()];
        for (int i = 0; i < accountNames.length; i++) {
            accountNames[i] = accounts.get(i).getName();
        }

        return accountNames;
    }

    private String[] convertCategoriesToStrings(List<Category> categories) {
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categoryNames.length; i++) {
            categoryNames[i] = categories.get(i).getName();
        }

        return categoryNames;
    }

    private String convertDayMonthToString(int value) {
        return value < 10 ? "0" + value : "" + value;
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