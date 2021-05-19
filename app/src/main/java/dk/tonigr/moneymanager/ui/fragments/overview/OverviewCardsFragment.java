package dk.tonigr.moneymanager.ui.fragments.overview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.data.adapters.CardAdapter;
import dk.tonigr.moneymanager.data.repositories.CategoryRepository;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;

public class OverviewCardsFragment extends Fragment {

    public OverviewCardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview_cards, container, false);
        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        RecyclerView cardsRv = view.findViewById(R.id.cardsRecyclerView);
        cardsRv.hasFixedSize();
        cardsRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        CardAdapter adapter = new CardAdapter();
        cardsRv.setAdapter(adapter);
        viewModel.getAllCategoriesWithExpenses().observe(getViewLifecycleOwner(), adapter::setCategories);

        return view;
    }
}