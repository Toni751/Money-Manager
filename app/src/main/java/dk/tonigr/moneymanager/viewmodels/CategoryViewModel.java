package dk.tonigr.moneymanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.data.repositories.CategoryRepository;
import dk.tonigr.moneymanager.models.CategoryCard;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.CategoryWithExpenses;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = CategoryRepository.getInstance(application);
    }

//    public List<CategoryCard> getCategoryCards() {
//        return categoryRepository.getCategoryCards();
//    }

    public void addCategory(Category category){
        categoryRepository.addCategory(category);
    }

    public LiveData<List<Category>> getCategories(){
        return categoryRepository.getCategories();
    }

    public void updateCategory(Category category){
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(Category category){
        categoryRepository.deleteCategory(category);
    }

    public LiveData<List<CategoryWithExpenses>> getAllCategoriesWithExpenses(){
        return categoryRepository.getAllCategoriesWithExpenses();
    }
}
