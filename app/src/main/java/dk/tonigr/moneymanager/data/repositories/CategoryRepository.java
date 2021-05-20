package dk.tonigr.moneymanager.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.tonigr.moneymanager.data.dao.CategoryDAO;
import dk.tonigr.moneymanager.data.dao.MoneyManagerDatabase;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;

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

    public LiveData<List<CategoryWithExpenses>> getAllCategoriesWithExpenses(){
        return allCategoriesWithExpenses;
    }

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
