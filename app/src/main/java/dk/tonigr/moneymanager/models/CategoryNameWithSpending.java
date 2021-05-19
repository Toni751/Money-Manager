package dk.tonigr.moneymanager.models;

public class CategoryNameWithSpending {
    private String categoryName;
    private double monthlySpending;

    public CategoryNameWithSpending(String categoryName, double monthlySpending) {
        this.categoryName = categoryName;
        this.monthlySpending = monthlySpending;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getMonthlySpending() {
        return monthlySpending;
    }
}
