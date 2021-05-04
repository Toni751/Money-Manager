package dk.tonigr.moneymanager.data.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import dk.tonigr.moneymanager.ui.fragments.overview.OverviewCardsFragment;
import dk.tonigr.moneymanager.ui.fragments.overview.OverviewGoalsFragment;
import dk.tonigr.moneymanager.ui.fragments.overview.OverviewPieChartFragment;

public class OverviewTabAdapter extends FragmentStateAdapter {
    public OverviewTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OverviewCardsFragment();
            case 1:
                return new OverviewPieChartFragment();
            case 2:
                return new OverviewGoalsFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
