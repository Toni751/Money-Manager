package dk.tonigr.moneymanager.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = navHostFragment.getNavController();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.overviewFragment).setOpenableLayout(drawerLayout).build();

        NavigationView navigationView = findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        MenuItem logoutItem = navigationView.getMenu().getItem(7);
        logoutItem.setOnMenuItemClickListener(menuItem ->  {
                viewModel.signOut();
                return false;
        });

        TextView usernameNavHeader = navigationView.getHeaderView(0).findViewById(R.id.nameTextView);
        TextView emailNavHeader = navigationView.getHeaderView(0).findViewById(R.id.emailTextView);

        usernameNavHeader.setText(viewModel.getCurrentUser().getValue().getDisplayName());
        emailNavHeader.setText(viewModel.getCurrentUser().getValue().getEmail());
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if(user == null) {
                goToSignInActivity();
            }
        });
    }

    private void goToSignInActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}