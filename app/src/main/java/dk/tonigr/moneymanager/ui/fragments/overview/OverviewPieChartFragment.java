package dk.tonigr.moneymanager.ui.fragments.overview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.CategoryCard;
import dk.tonigr.moneymanager.models.CategoryNameWithSpending;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;
import dk.tonigr.moneymanager.models.db.Expense;
import dk.tonigr.moneymanager.util.ChartDataCache;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;

public class OverviewPieChartFragment extends Fragment {

    private List<CategoryNameWithSpending> categories = new ArrayList<>();

    public OverviewPieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview_pie_chart, container, false);
        // CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categories = ChartDataCache.getInstance().getCategoryWithExpenses();
        AnyChartView anyChartView = view.findViewById(R.id.pieChart);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();

        for (CategoryNameWithSpending category : categories) {
            data.add(new ValueDataEntry(category.getCategoryName(), category.getMonthlySpending()));
        }

        pie.data(data);
        pie.labels().position("outside");
        pie.legend()
                .position("center-top")
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                .align(Align.CENTER);

        anyChartView.setChart(pie);

        return view;
    }

    private String getCurrentMonthString(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        currentMonth++;
        String searchString = currentYear + "-";
        if(currentMonth < 10) searchString += "0";
        searchString += currentMonth;
        searchString += "-";
        return searchString;
    }
}