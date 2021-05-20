package dk.tonigr.moneymanager.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Goal;
import dk.tonigr.moneymanager.util.DateStringConverter;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class GoalOverviewAdapter extends RecyclerView.Adapter<GoalOverviewAdapter.GoalOverviewViewHolder> {

    private List<Goal> goals = new ArrayList<>();
    private GoalViewModel viewModel;

    public GoalOverviewAdapter(GoalViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public GoalOverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GoalOverviewViewHolder(inflater.inflate(R.layout.overview_goal_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalOverviewViewHolder holder, int position) {
        Goal currentGoal = goals.get(position);
        DecimalFormat formatter = new DecimalFormat("0.##");

        String header = "Save " + formatter.format(currentGoal.getTarget_amount()) + PreferenceManager.getDefaultSharedPreferences(holder.currentText.getContext()).getString("currency", "") +" for " + currentGoal.getName() + " by " + DateStringConverter.convertDbDateToUiDate(currentGoal.getDue_date());
        holder.headerText.setText(header);

        double percentage = currentGoal.getCurrent_amount() * 100 / currentGoal.getTarget_amount();
        holder.progressBar.setProgress((int) percentage);

        String current = formatter.format(currentGoal.getCurrent_amount()) + "dkk (" + formatter.format(percentage) + "%) Complete - Started " + DateStringConverter.convertDbDateToUiDate(currentGoal.getStart_date());
        holder.currentText.setText(current);
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void setGoals(List<Goal> goals){
        this.goals = goals;
        notifyDataSetChanged();
    }

    class GoalOverviewViewHolder extends RecyclerView.ViewHolder{

        public TextView headerText;
        public ProgressBar progressBar;
        public TextView currentText;

        public GoalOverviewViewHolder(@NonNull View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.goalCardHeader);
            progressBar = itemView.findViewById(R.id.goalCardProgressBar);
            currentText = itemView.findViewById(R.id.goalCardCurrent);
        }
    }
}
