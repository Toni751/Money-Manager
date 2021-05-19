package dk.tonigr.moneymanager.ui.fragments.accounts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.viewmodels.AccountViewModel;

public class AddAccountFragment extends Fragment {

    private EditText name;
    private EditText amount;
    private EditText income;
    private Spinner spinner;

    private TextView nameError;
    private TextView amountError;
    private TextView incomeError;

    public AddAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);
        AccountViewModel viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        String currency = PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("currency", "");

        TextView amountTextView = view.findViewById(R.id.addAccountAmountTextView);
        amountTextView.setText("Amount (" + currency + ")");

        name = view.findViewById(R.id.addAccountName);
        amount = view.findViewById(R.id.addAccountAmount);
        income = view.findViewById(R.id.addAccountIncome);

        amount.setHint("Enter amount in " + currency + "..");

        nameError = view.findViewById(R.id.addAccountNameError);
        amountError = view.findViewById(R.id.addAccountAmountError);
        incomeError = view.findViewById(R.id.addAccountIncomeError);

        nameError.setVisibility(View.GONE);
        amountError.setVisibility(View.GONE);
        incomeError.setVisibility(View.GONE);

        spinner = view.findViewById(R.id.accountTypeSpinner);
        Button button = view.findViewById(R.id.submitAccountButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(v -> {
            boolean isValid = validateForm();
            if(!isValid){
                return;
            }

            Account account = new Account(name.getText().toString(), spinner.getSelectedItem().toString(), Double.parseDouble(amount.getText().toString()), Double.parseDouble(income.getText().toString()));
            Log.i("my_account", account.toString());
            viewModel.addAccount(account);
            Navigation.findNavController(view).navigate(R.id.action_addAccountFragment_to_accountsFragment);
        });

        return view;
    }

    private boolean validateForm() {
        boolean isValid = true;

        if(name.getText().toString().equals("")){
            nameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            nameError.setVisibility(View.GONE);
        }

        if(amount.getText().toString().equals("")){
            amountError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            amountError.setVisibility(View.GONE);
        }

        if(income.getText().toString().equals("")){
            incomeError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            incomeError.setVisibility(View.GONE);
        }

        return isValid;
    }
}