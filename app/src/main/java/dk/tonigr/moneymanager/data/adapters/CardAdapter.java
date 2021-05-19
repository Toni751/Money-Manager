package dk.tonigr.moneymanager.data.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.CategoryCard;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;
import dk.tonigr.moneymanager.models.db.Expense;
import dk.tonigr.moneymanager.util.ChartDataCache;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CategoryWithExpenses> categories = new ArrayList<>();

    public CardAdapter() {}

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CardViewHolder(inflater.inflate(R.layout.card_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardViewHolder holder, int position) {
        // CategoryCard currentCategory = categories.get(position);
        CategoryWithExpenses category = categories.get(position);
        DecimalFormat formatter = new DecimalFormat("0.##");

        holder.name.setText("Category: " + category.category.getName());
        double totalSpent = 0;
        String currentMonthString = getCurrentMonthString();
        for (Expense expense: category.expenses) {
            if(expense.getDateTime().startsWith(currentMonthString))
            totalSpent += expense.getAmount();
        }
        double percentage = totalSpent * 100 / category.category.getMonthly_goal();
        String colorString = "#";
        if(percentage < 60) {
            colorString = "#00FF00";
        } else if(percentage >= 60 && percentage <= 90) {
            colorString = "#FFC107";
        } else {
            colorString = "#FF0000";
        }

        holder.cardView.setStrokeColor(Color.parseColor(colorString));

        String percentageSpent = formatter.format(percentage);
        String spentAmountString = "Spent: " + formatter.format(totalSpent) + " (" + percentageSpent + "%)";
        holder.spentAmount.setText(spentAmountString);

        holder.monthlyAmount.setText("Monthly limit: " + formatter.format(category.category.getMonthly_goal()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<CategoryWithExpenses> categories){
        this.categories = categories;
        ChartDataCache.getInstance().setCategoryWithExpenses(categories);
        notifyDataSetChanged();
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

    class CardViewHolder extends RecyclerView.ViewHolder {

        public MaterialCardView cardView;
        public TextView name;
        public TextView spentAmount;
        public TextView monthlyAmount;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            name = itemView.findViewById(R.id.cardNameTextView);
            spentAmount = itemView.findViewById(R.id.cardSpentAmountTextView);
            monthlyAmount = itemView.findViewById(R.id.cardMonthlyAmountTextView);
        }
    }
}
