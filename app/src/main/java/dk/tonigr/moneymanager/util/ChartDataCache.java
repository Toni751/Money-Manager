package dk.tonigr.moneymanager.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dk.tonigr.moneymanager.models.CategoryNameWithSpending;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;
import dk.tonigr.moneymanager.models.db.Expense;

public class ChartDataCache {
    private List<CategoryNameWithSpending> categoryWithExpenses;
    private static ChartDataCache instance;

    private ChartDataCache(){
        categoryWithExpenses = new ArrayList<>();
    }

    public static ChartDataCache getInstance(){
        if(instance == null){
            instance = new ChartDataCache();
        }

        return instance;
    }

    public List<CategoryNameWithSpending> getCategoryWithExpenses() {
        return categoryWithExpenses;
    }

    public void setCategoryWithExpenses(List<CategoryWithExpenses> categoryWithExpenses) {
        this.categoryWithExpenses = new ArrayList<>();
        for (CategoryWithExpenses category : categoryWithExpenses) {
            double totalSpent = 0;
            String currentMonthString = getCurrentMonthString();
            for (Expense expense: category.expenses) {
                if(expense.getDateTime().startsWith(currentMonthString))
                    totalSpent += expense.getAmount();
            }
            this.categoryWithExpenses.add(new CategoryNameWithSpending(category.category.getName(), totalSpent));
        }
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
