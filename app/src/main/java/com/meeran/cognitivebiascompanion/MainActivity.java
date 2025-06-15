package com.meeran.cognitivebiascompanion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Temporarily disable EdgeToEdge to fix header visibility
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        setupNavigation();
        
        // Simplified window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply minimal padding only where needed
            v.setPadding(0, 0, 0, 0);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void setupNavigation() {
        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Setup nav controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Setup app bar configuration with top-level destinations
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bias_scanner, R.id.nav_news_analysis, 
                R.id.nav_learning, R.id.nav_analytics)
                .setOpenableLayout(drawerLayout)
                .build();

        // Connect navigation components
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Add manual click handler for bottom navigation to ensure it works
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                navController.navigate(R.id.nav_home);
                return true;
            } else if (itemId == R.id.nav_bias_scanner) {
                navController.navigate(R.id.nav_bias_scanner);
                return true;
            } else if (itemId == R.id.nav_news_analysis) {
                navController.navigate(R.id.nav_news_analysis);
                return true;
            } else if (itemId == R.id.nav_learning) {
                navController.navigate(R.id.nav_learning);
                return true;
            } else if (itemId == R.id.nav_analytics) {
                navController.navigate(R.id.nav_analytics);
                return true;
            }
            return false;
        });

        // Setup drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}