package dk.tonigr.moneymanager.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.Goal;
import dk.tonigr.moneymanager.viewmodels.CategoryViewModel;

public class CategoryCardAdapter extends RecyclerView.Adapter<CategoryCardAdapter.CategoryCardViewHolder> {
    private List<Category> categories = new ArrayList<>();
    private CategoryViewModel viewModel;
    private CategoryCardAdapterOnClickListener listener;

    public CategoryCardAdapter(CategoryViewModel viewModel, CategoryCardAdapterOnClickListener listener) {
        this.viewModel = viewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CategoryCardViewHolder(inflater.inflate(R.layout.category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardAdapter.CategoryCardViewHolder holder, int position) {
        Category currentCategory = categories.get(position);
        String currency = PreferenceManager.getDefaultSharedPreferences(holder.categoryName.getContext()).getString("currency", "");

        holder.categoryName.setText(currentCategory.getName());
        holder.categoryMonthlyGoal.setText("Monthly goal: " + currentCategory.getMonthly_goal() + " (" + currency + ")");
        holder.editButton.setOnClickListener(view -> listener.onCategoryCardClick(currentCategory));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    class CategoryCardViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryName;
        public TextView categoryMonthlyGoal;
        public ImageButton editButton;

        public CategoryCardViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryCardName);
            categoryMonthlyGoal = itemView.findViewById(R.id.categoryCardMonthlyGoal);
            editButton = itemView.findViewById(R.id.editCategoryButton);
        }
    }

    public interface CategoryCardAdapterOnClickListener{
        void onCategoryCardClick(Category category);
    }
}
