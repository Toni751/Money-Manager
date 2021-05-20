package dk.tonigr.moneymanager.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Category;
import dk.tonigr.moneymanager.models.db.Expense;
import dk.tonigr.moneymanager.util.DateStringConverter;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<Expense> expenses = new ArrayList<>();
    private ExpenseAdapterOnClickListener listener;

    public ExpenseAdapter(ExpenseAdapterOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ExpenseViewHolder(inflater.inflate(R.layout.expense_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        Expense currentExpense = expenses.get(position);
        DecimalFormat formatter = new DecimalFormat("0.##");
        String currency = PreferenceManager.getDefaultSharedPreferences(holder.expenseName.getContext()).getString("currency", "");
        String[] dateSplit = currentExpense.getDateTime().split(" ");

        holder.expenseName.setText(currentExpense.getName());
        holder.expenseAmount.setText(formatter.format(currentExpense.getAmount()) + "(" + currency + ")");
        holder.expenseDate.setText(DateStringConverter.convertDbDateToUiDate(dateSplit[0]) + " " + dateSplit[1]);
        holder.cardView.setOnClickListener(view -> listener.onExpenseCardClick(currentExpense));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private TextView expenseName;
        private TextView expenseAmount;
        private TextView expenseDate;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.expenseItemCard);
            expenseName = itemView.findViewById(R.id.expenseItemName);
            expenseAmount = itemView.findViewById(R.id.expenseItemAmount);
            expenseDate = itemView.findViewById(R.id.expenseItemDate);
        }
    }

    public interface ExpenseAdapterOnClickListener{
        void onExpenseCardClick(Expense expense);
    }
}
