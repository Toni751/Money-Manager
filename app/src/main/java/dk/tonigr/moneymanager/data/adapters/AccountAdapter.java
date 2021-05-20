package dk.tonigr.moneymanager.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.viewmodels.AccountViewModel;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accounts = new ArrayList<>();
    private AccountViewModel viewModel;

    public AccountAdapter(AccountViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new AccountViewHolder(inflater.inflate(R.layout.account_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        DecimalFormat formatter = new DecimalFormat("0.##");
        String currency = PreferenceManager.getDefaultSharedPreferences(holder.accountHeader.getContext()).getString("currency", "");

        if (account.getType().equals("Cash")) {
            holder.accountTypeIcon.setImageResource(R.drawable.ic_baseline_monetization_on_24);
        } else {
            holder.accountTypeIcon.setImageResource(R.drawable.ic_baseline_credit_card_24);
        }

        holder.accountHeader.setText(account.getName());
        holder.accountAmount.setText("Amount: " + formatter.format(account.getAmount()) + currency);
        holder.accountIncome.setText("Monthly income: " + formatter.format(account.getMonthly_income()) + currency);
        holder.accountType.setText("Type: " + account.getType());

        holder.accountHeaderET.setText(account.getName());
        holder.accountAmountET.setText(formatter.format(account.getAmount()));
        holder.accountIncomeET.setText(formatter.format(account.getMonthly_income()));
        holder.spinner.setSelection(calculateSpinnerIndex(account.getType()));

        holder.accountHeaderET.setVisibility(View.GONE);
        holder.accountAmountET.setVisibility(View.GONE);
        holder.accountIncomeET.setVisibility(View.GONE);
        holder.accountTypeLabel.setVisibility(View.GONE);
        holder.spinner.setVisibility(View.GONE);

        holder.confirmEditAccountButton.setVisibility(View.GONE);
        holder.deleteAccountButton.setVisibility(View.GONE);

        holder.editAccountButton.setOnClickListener(view -> {
            holder.accountHeader.setVisibility(View.GONE);
            holder.accountAmount.setVisibility(View.GONE);
            holder.accountIncome.setVisibility(View.GONE);
            holder.accountType.setVisibility(View.GONE);
            holder.accountTypeLabel.setVisibility(View.VISIBLE);

            holder.accountHeaderET.setVisibility(View.VISIBLE);
            holder.accountAmountET.setVisibility(View.VISIBLE);
            holder.accountIncomeET.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.VISIBLE);

            holder.editAccountButton.setVisibility(View.GONE);
            holder.confirmEditAccountButton.setVisibility(View.VISIBLE);
            holder.deleteAccountButton.setVisibility(View.VISIBLE);
        });

        holder.confirmEditAccountButton.setOnClickListener(view -> {
            account.setName(holder.accountHeaderET.getText().toString());
            account.setAmount(Double.parseDouble(holder.accountAmountET.getText().toString()));
            account.setMonthly_income(Double.parseDouble(holder.accountIncomeET.getText().toString()));
            account.setType(holder.spinner.getSelectedItem().toString());

            viewModel.updateAccount(account);
            clearViewsVisibilityAfterOperation(holder);
        });

        holder.deleteAccountButton.setOnClickListener(view -> {
            clearViewsVisibilityAfterOperation(holder);
            viewModel.deleteAccount(account);
        });
    }

    private void clearViewsVisibilityAfterOperation(AccountAdapter.AccountViewHolder holder){
        holder.accountHeaderET.setVisibility(View.GONE);
        holder.accountAmountET.setVisibility(View.GONE);
        holder.accountIncomeET.setVisibility(View.GONE);
        holder.spinner.setVisibility(View.GONE);

        holder.accountHeader.setVisibility(View.VISIBLE);
        holder.accountAmount.setVisibility(View.VISIBLE);
        holder.accountIncome.setVisibility(View.VISIBLE);
        holder.accountType.setVisibility(View.VISIBLE);
        holder.accountTypeLabel.setVisibility(View.GONE);

        holder.editAccountButton.setVisibility(View.VISIBLE);
        holder.confirmEditAccountButton.setVisibility(View.GONE);
        holder.deleteAccountButton.setVisibility(View.GONE);
    }

    private int calculateSpinnerIndex(String type) {
        switch (type){
            case "Credit Card":
                return 0;
            case "Debit Card":
                return 1;
            case "Savings Account":
                return 2;
            case "Cash":
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {

        public ImageView accountTypeIcon;
        public TextView accountHeader;
        public TextView accountAmount;
        public TextView accountIncome;
        public TextView accountType;
        public TextView accountTypeLabel;

        public EditText accountHeaderET;
        public EditText accountAmountET;
        public EditText accountIncomeET;
        public Spinner spinner;

        public ImageButton editAccountButton;
        public ImageButton confirmEditAccountButton;
        public ImageButton deleteAccountButton;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountTypeIcon = itemView.findViewById(R.id.accountTypeIcon);
            accountHeader = itemView.findViewById(R.id.accountViewHeaderText);
            accountAmount = itemView.findViewById(R.id.accountViewAmountText);
            accountIncome = itemView.findViewById(R.id.accountViewIncomeText);
            accountType = itemView.findViewById(R.id.accountViewTypeText);
            accountTypeLabel = itemView.findViewById(R.id.accountViewTypeTextView);

            accountHeaderET = itemView.findViewById(R.id.accountViewHeaderEditText);
            accountAmountET = itemView.findViewById(R.id.accountViewAmountEditText);
            accountIncomeET = itemView.findViewById(R.id.accountViewIncomeEditText);
            spinner = itemView.findViewById(R.id.editAccountTypeSpinner);

            editAccountButton = itemView.findViewById(R.id.editAccountButton);
            confirmEditAccountButton = itemView.findViewById(R.id.confirmEditAccountButton);
            deleteAccountButton = itemView.findViewById(R.id.deleteAccountButton);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(),
                    R.array.account_type, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}
