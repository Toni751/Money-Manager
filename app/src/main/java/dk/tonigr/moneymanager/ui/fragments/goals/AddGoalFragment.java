package dk.tonigr.moneymanager.ui.fragments.goals;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.models.db.Goal;
import dk.tonigr.moneymanager.viewmodels.GoalViewModel;

public class AddGoalFragment extends Fragment {
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

    public AddGoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_goal, container, false);
        GoalViewModel viewModel = new ViewModelProvider(this).get(GoalViewModel.class);
        String currency = PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("currency", "");

        TextView targetAmountTextView = view.findViewById(R.id.addGoalTargetAmountTextView);
        TextView currentAmountTextView = view.findViewById(R.id.addGoalCurrentAmountTextView);
        targetAmountTextView.setText("Target amount (" + currency + ")");
        currentAmountTextView.setText("Current amount (" + currency + ")");

        name = view.findViewById(R.id.addGoalName);
        targetAmount = view.findViewById(R.id.addGoalTargetAmount);
        currentAmount = view.findViewById(R.id.addGoalCurrentAmount);
        dueDate = view.findViewById(R.id.addGoalDueDate);
        startDate = view.findViewById(R.id.addGoalStartDate);

        nameError = view.findViewById(R.id.addGoalNameError);
        targetAmountError = view.findViewById(R.id.addGoalTargetAmountError);
        currentAmountError = view.findViewById(R.id.addGoalCurrentAmountError);
        dueDateError = view.findViewById(R.id.addGoalDueDateError);
        startDateError = view.findViewById(R.id.addGoalStartDateError);

        nameError.setVisibility(View.GONE);
        targetAmountError.setVisibility(View.GONE);
        currentAmountError.setVisibility(View.GONE);
        dueDateError.setVisibility(View.GONE);
        startDateError.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        final String[] dueDateString = {""};
        dueDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> {
                month += 1;
                dueDateString[0] = year + "-" + convertDayMonthToString(month) + "-" + convertDayMonthToString(day);
                dueDate.setText(convertDayMonthToString(day) + "/" + convertDayMonthToString(month) + "/" + year);
            }, currentYear, currentMonth, currentDay);
            dialog.show();
        });

        final String[] startDateString = {""};
        startDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> {
                month += 1;
                startDateString[0] = year + "-" + convertDayMonthToString(month) + "-" + convertDayMonthToString(day);
                startDate.setText(convertDayMonthToString(day) + "/" + convertDayMonthToString(month) + "/" + year);
            }, currentYear, currentMonth, currentDay);
            dialog.show();
        });

        Button button = view.findViewById(R.id.submitGoalButton);
        button.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }

            SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");

            Goal goal = new Goal(name.getText().toString(), Double.parseDouble(targetAmount.getText().toString()), Double.parseDouble(currentAmount.getText().toString()),
                    startDateString[0], dueDateString[0]);
            viewModel.addGoal(goal);
            Navigation.findNavController(view).navigate(R.id.action_addGoalFragment_to_goalsFragment);

        });

        return view;
    }

    private String convertDayMonthToString(int value){
        return value < 10 ?  "0" + value : "" + value;
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