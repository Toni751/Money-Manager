package dk.tonigr.moneymanager.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.tonigr.moneymanager.data.dao.CategoryDAO;
import dk.tonigr.moneymanager.data.dao.MoneyManagerDatabase;
import dk.tonigr.moneymanager.models.CategoryCard;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;
import dk.tonigr.moneymanager.models.db.Expense;

public class CategoryRepository {
    private static CategoryRepository instance;
    private CategoryDAO categoryDAO;
    private ExecutorService executorService;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<CategoryWithExpenses>> allCategoriesWithExpenses;

    private CategoryRepository(Application application) {
        categoryDAO = MoneyManagerDatabase.getInstance(application).categoryDAO();
        allCategories = categoryDAO.getAllCategories();
        allCategoriesWithExpenses = categoryDAO.getAllCategoriesWithExpenses();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static CategoryRepository getInstance(Application application){
        if(instance == null) {
            instance = new CategoryRepository(application);
        }

        return instance;
    }

    public List<CategoryCard> getCategoryCards() {
        List<CategoryCard> categoryCards = new ArrayList<>();
        categoryCards.add(new CategoryCard("Bills", 1500, 2000));
        categoryCards.add(new CategoryCard("Health", 100, 500));
        categoryCards.add(new CategoryCard("Education", 150, 200));
        categoryCards.add(new CategoryCard("Clothes", 700, 500));
        categoryCards.add(new CategoryCard("Groceries", 700, 1000));
        categoryCards.add(new CategoryCard("Leisure", 400, 600));
        categoryCards.add(new CategoryCard("Transportation", 200, 400));
        categoryCards.add(new CategoryCard("Sport", 300, 300));
        return categoryCards;
    }

    public LiveData<List<CategoryWithExpenses>> getAllCategoriesWithExpenses(){
//        String searchString = getCurrentMonthString();
//        allCategoriesWithExpenses = categoryDAO.getAllCategoriesWithExpenses();
//
//        Log.i("yess", searchString);
        //allCategoriesWithExpenses = categoryDAO.getAllCategoriesWithExpensesForMonth("2021-05-01");
//        List<CategoryWithExpenses> temp = allCategoriesWithExpenses.getValue();
//        for (int i = 0; i < temp.size(); i++) {
//            List<Expense> expenses = temp.get(i).expenses;
//            for (int j = 0; j < expenses.size(); j++) {
//                if(!expenses.get(i).getDateTime().startsWith(searchString)){
//                    expenses.remove(i);
//                }
//            }
//            // temp.get(i).expenses = expenses;
//        }
        return allCategoriesWithExpenses;
    }

//    private String getCurrentMonthString(){
//        Calendar calendar = Calendar.getInstance();
//        int currentYear = calendar.get(Calendar.YEAR);
//        int currentMonth = calendar.get(Calendar.MONTH);
//        currentMonth++;
//        String searchString = currentYear + "-";
//        if(currentMonth < 10) searchString += "0";
//        searchString += currentMonth;
//        searchString += "-";
//        return searchString;
//    }

    public void addCategory(Category category){
        executorService.execute(() -> categoryDAO.insert(category));
    }

    public LiveData<List<Category>> getCategories(){
        allCategories = categoryDAO.getAllCategories();
        return allCategories;
    }

    public void updateCategory(Category category){
        executorService.execute(() -> categoryDAO.update(category));
    }

    public void deleteCategory(Category category){
        executorService.execute(() -> categoryDAO.delete(category));
    }
}
