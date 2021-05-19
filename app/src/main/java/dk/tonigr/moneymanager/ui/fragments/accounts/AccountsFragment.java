package dk.tonigr.moneymanager.ui.fragments.accounts;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.data.adapters.AccountAdapter;
import dk.tonigr.moneymanager.data.adapters.CardAdapter;
import dk.tonigr.moneymanager.models.db.Account;
import dk.tonigr.moneymanager.viewmodels.AccountViewModel;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;

public class AccountsFragment extends Fragment {

    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        AccountViewModel viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        RecyclerView accountsRV = view.findViewById(R.id.accountsRecyclerView);
        accountsRV.hasFixedSize();
        accountsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        AccountAdapter adapter = new AccountAdapter(viewModel);
        accountsRV.setAdapter(adapter);
        viewModel.getAccounts().observe(getViewLifecycleOwner(), adapter::setAccounts);

        Button button = view.findViewById(R.id.addAccountButton);
        button.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_accountsFragment_to_addAccountFragment));
        return view;
    }
}