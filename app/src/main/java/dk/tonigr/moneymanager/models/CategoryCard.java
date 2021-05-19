package dk.tonigr.moneymanager.models;

public class CategoryCard {
    private String categoryName;
    private double spentAmount;
    private double monthlyAmount;

    public CategoryCard(String categoryName, double spentAmount, double monthlyAmount) {
        this.categoryName = categoryName;
        this.spentAmount = spentAmount;
        this.monthlyAmount = monthlyAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public double getMonthlyAmount() {
        return monthlyAmount;
    }
}
