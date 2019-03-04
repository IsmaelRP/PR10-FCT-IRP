package com.practica.ismael.pr10_fct_irp.ui.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements ToolbarConf {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
    private SharedPreferences preferences;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        drawerLayout = findViewById(R.id.drawer_navigation);
        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> setTitle(destination.getLabel()));
        if (drawerLayout != null) {
            // Navigation drawer mode.
            setupNavigationDrawer();
        } else {
            // BottomNavigationView mode
            setupBottomNavigationView();
        }
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mainViewModel.setHomePage(preferences.getString(getString(R.string.mainDest), getString(R.string.desDefault)));
        mainViewModel.setVisitDays(preferences.getInt(getString(R.string.daysKey), 15));
        setupNavigationGraph();
    }

    private void setupNavigationGraph() {
        int startDestinationResId;
        NavHostFragment navHost =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                        R.id.navHostFragment);
        NavController navController = navHost.getNavController();
        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.main_navigation);

        switch (mainViewModel.getHomePage()) {
            case "next":
                startDestinationResId = R.id.nextMeetingsFragment;
                break;
            case "stud":
                startDestinationResId = R.id.studentsFragment;
                break;
            case "comp":
                startDestinationResId = R.id.companiesFragment;
                break;
            case "meet":
                startDestinationResId = R.id.meetingsFragment;
                break;
            default:
                startDestinationResId = R.id.nextMeetingsFragment;
                break;
        }

        navGraph.setStartDestination(startDestinationResId);
        navController.setGraph(navGraph);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = ActivityCompat.requireViewById(this,
                R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.companiesFragment, R.id.studentsFragment,
                R.id.meetingsFragment, R.id.nextMeetingsFragment).build();
    }

    private void setupNavigationDrawer() {
        NavigationView navigationView = ActivityCompat.requireViewById(this, R.id.navigation_view);
        NavigationUI.setupWithNavController(navigationView, navController);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.companiesFragment, R.id.studentsFragment,
                R.id.meetingsFragment, R.id.nextMeetingsFragment).setDrawerLayout(drawerLayout).build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onConfigure(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }


}
