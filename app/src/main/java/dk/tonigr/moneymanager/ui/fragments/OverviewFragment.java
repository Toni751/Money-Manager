package dk.tonigr.moneymanager.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.data.adapters.OverviewTabAdapter;

public class OverviewFragment extends Fragment {

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> Toast.makeText(getContext(), "Overview fab clicked", Toast.LENGTH_SHORT).show());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        OverviewTabAdapter adapter = new OverviewTabAdapter(this);
        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Cards");
                    tab.setIcon(R.drawable.ic_baseline_list_24);
                    break;
                case 1:
                    tab.setText("Chart");
                    tab.setIcon(R.drawable.ic_baseline_pie_chart_24);
                    break;
                case 2:
                    tab.setText("Goals");
                    tab.setIcon(R.drawable.ic_baseline_done_24);
                    break;
                default:
                    tab.setText("Nope " + position);
            }
        })).attach();
    }
}