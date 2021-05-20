package dk.tonigr.moneymanager.ui.fragments.expenses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.data.adapters.ExpenseAdapter;
import dk.tonigr.moneymanager.viewmodels.ExpenseViewModel;

public class TransactionsFragment extends Fragment {

    public TransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        ExpenseViewModel viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        RecyclerView expensesRV = view.findViewById(R.id.expensesRecyclerView);
        expensesRV.hasFixedSize();
        expensesRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ExpenseAdapter adapter = new ExpenseAdapter(expense -> {
            TransactionsFragmentDirections.ActionTransactionsFragmentToEditExpenseFragment action = TransactionsFragmentDirections.actionTransactionsFragmentToEditExpenseFragment(expense);
            Navigation.findNavController(view).navigate(action);
        });

        expensesRV.setAdapter(adapter);
        viewModel.getAllExpenses().observe(getViewLifecycleOwner(), adapter::setExpenses);

        return view;
    }
}