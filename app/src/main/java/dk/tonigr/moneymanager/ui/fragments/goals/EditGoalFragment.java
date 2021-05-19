package dk.tonigr.moneymanager.ui.fragments.goals;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Goal;
import dk.tonigr.moneymanager.util.DateStringConverter;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class EditGoalFragment extends Fragment {
    private EditText name;
    private EditText targetAmount;
    private EditText currentAmount;
    private EditText dueDate;
    private EditText startDate;

    private TextView nameError;
    private TextView targetAmountError;
    private TextView currentAmountError;
    private TextView dueDateError;
    private TextView startDateError;

    public EditGoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_goal, container, false);
        Goal goal = EditGoalFragmentArgs.fromBundle(getArguments()).getGoal();
        GoalViewModel viewModel = new ViewModelProvider(this).get(GoalViewModel.class);
        // SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");

        name = view.findViewById(R.id.editGoalName);
        targetAmount = view.findViewById(R.id.editGoalTargetAmount);
        currentAmount = view.findViewById(R.id.editGoalCurrentAmount);
        dueDate = view.findViewById(R.id.editGoalDueDate);
        startDate = view.findViewById(R.id.editGoalStartDate);

        name.setText(goal.getName());
        targetAmount.setText(String.valueOf(goal.getTarget_amount()));
        currentAmount.setText(String.valueOf(goal.getCurrent_amount()));
        dueDate.setText(DateStringConverter.convertDbDateToUiDate(goal.getDue_date()));
        startDate.setText(DateStringConverter.convertDbDateToUiDate(goal.getStart_date()));

        nameError = view.findViewById(R.id.editGoalNameError);
        targetAmountError = view.findViewById(R.id.editGoalTargetAmountError);
        currentAmountError = view.findViewById(R.id.editGoalCurrentAmountError);
        dueDateError = view.findViewById(R.id.editGoalDueDateError);
        startDateError = view.findViewById(R.id.editGoalStartDateError);

        nameError.setVisibility(View.GONE);
        targetAmountError.setVisibility(View.GONE);
        currentAmountError.setVisibility(View.GONE);
        dueDateError.setVisibility(View.GONE);
        startDateError.setVisibility(View.GONE);

        String dueDateFormatted = DateStringConverter.convertDbDateToUiDate(goal.getDue_date());
        final String[] dueDateString = {goal.getDue_date()};
        dueDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> {
                month += 1;
                dueDateString[0] = year + "-" + convertDayMonthToString(month) + "-" + convertDayMonthToString(day);
                dueDate.setText(convertDayMonthToString(day) + "/" + convertDayMonthToString(month) + "/" + year);
            }, Integer.parseInt(dueDateFormatted.substring(6)), Integer.parseInt(dueDateFormatted.substring(3, 5)) - 1, Integer.parseInt(dueDateFormatted.substring(0, 2)));
            dialog.show();
        });

        String startDateFormatted = DateStringConverter.convertDbDateToUiDate(goal.getStart_date());
        final String[] startDateString = {goal.getStart_date()};
        startDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> {
                month += 1;
                startDateString[0] = year + "-" + convertDayMonthToString(month) + "-" + convertDayMonthToString(day);
                startDate.setText(convertDayMonthToString(day) + "/" + convertDayMonthToString(month) + "/" + year);
            }, Integer.parseInt(startDateFormatted.substring(6)), Integer.parseInt(startDateFormatted.substring(3, 5)) - 1, Integer.parseInt(startDateFormatted.substring(0, 2)));
            dialog.show();
        });

        Button editButton = view.findViewById(R.id.submitEditGoalButton);
        editButton.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }

            goal.setName(name.getText().toString());
            goal.setTarget_amount(Double.parseDouble(targetAmount.getText().toString()));
            goal.setCurrent_amount(Double.parseDouble(currentAmount.getText().toString()));
            goal.setStart_date(startDateString[0]);
            goal.setDue_date(dueDateString[0]);

            viewModel.updateGoal(goal);
            Navigation.findNavController(view).navigate(R.id.action_editGoalFragment_to_goalsFragment);
        });

        Button deleteButton = view.findViewById(R.id.deleteGoalButton);
        deleteButton.setOnClickListener(v -> {
            viewModel.deleteGoal(goal);
            Navigation.findNavController(view).navigate(R.id.action_editGoalFragment_to_goalsFragment);
        });

        return view;
    }

    private String convertDayMonthToString(int value) {
        return value < 10 ? "0" + value : "" + value;
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (name.getText().toString().equals("")) {
            nameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            nameError.setVisibility(View.GONE);
        }

        if (targetAmount.getText().toString().equals("")) {
            targetAmountError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            targetAmountError.setVisibility(View.GONE);
        }

        if (currentAmount.getText().toString().equals("")) {
            currentAmountError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            currentAmountError.setVisibility(View.GONE);
        }

        if (dueDate.getText().toString().equals("")) {
            dueDateError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            dueDateError.setVisibility(View.GONE);
        }

        if (startDate.getText().toString().equals("")) {
            startDateError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            startDateError.setVisibility(View.GONE);
        }

        return isValid;
    }
}