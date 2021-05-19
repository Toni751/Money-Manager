package dk.tonigr.moneymanager.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Goal;
import dk.tonigr.moneymanager.util.DateStringConverter;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class GoalCardAdapter extends RecyclerView.Adapter<GoalCardAdapter.GoalViewHolder>{
    private List<Goal> goals = new ArrayList<>();
    private GoalViewModel viewModel;
    private GoalCardAdapterOnClickListener listener;

    public GoalCardAdapter(GoalViewModel viewModel, GoalCardAdapterOnClickListener listener) {
        this.viewModel = viewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GoalViewHolder(inflater.inflate(R.layout.goal_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalCardAdapter.GoalViewHolder holder, int position) {
        Goal currentGoal = goals.get(position);
        DecimalFormat formatter = new DecimalFormat("0.##");
        String currency = PreferenceManager.getDefaultSharedPreferences(holder.goalName.getContext()).getString("currency", "");

        holder.goalName.setText(currentGoal.getName());
        holder.goalAmount.setText(formatter.format(currentGoal.getCurrent_amount()) + " / " + formatter.format(currentGoal.getTarget_amount()) + " (" + currency + ")");
        holder.goalDate.setText(DateStringConverter.convertDbDateToUiDate(currentGoal.getStart_date()) + " / " + DateStringConverter.convertDbDateToUiDate(currentGoal.getDue_date()));

        holder.cardView.setOnClickListener(v -> listener.onGoalCardClick(currentGoal));
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void setGoals(List<Goal> goals){
        this.goals = goals;
        notifyDataSetChanged();
    }

    class GoalViewHolder extends RecyclerView.ViewHolder{

        public MaterialCardView cardView;
        public TextView goalName;
        public TextView goalAmount;
        public TextView goalDate;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.goalCard);
            goalName = itemView.findViewById(R.id.cardGoalName);
            goalAmount = itemView.findViewById(R.id.cardGoalAmount);
            goalDate = itemView.findViewById(R.id.cardGoalDate);
        }
    }

    public interface GoalCardAdapterOnClickListener{
        //void onGoalCardClick(int goal_id, String name, double target_amount, double current_amount, Date due_date, Date start_date);
        void onGoalCardClick(Goal goal);
    }
}
