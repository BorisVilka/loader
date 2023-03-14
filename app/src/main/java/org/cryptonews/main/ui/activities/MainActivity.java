package org.cryptonews.main.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.navigation.NavigationView;

import org.cryptonews.main.MyApp;
import org.cryptonews.main.R;
import org.cryptonews.main.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private int flag;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        flag= 0;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("TAG",getTheme().toString());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow,R.id.nav_prognose,R.id.nav_settings,R.id.nav_favorites,R.id.nav_terms)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        binding.navView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId()==R.id.nav_gallery) {
                binding.drawerLayout.close();
                switch (navController.getCurrentDestination().getId()) {
                    case R.id.nav_home:
                        flag= 0;
                        navController.navigate(R.id.action_nav_home_to_nav_gallery);
                        break;
                    case R.id.nav_prognose:
                        flag= 1;
                        navController.navigate(R.id.action_nav_prognose_to_nav_gallery);
                        break;
                    case R.id.nav_slideshow:
                        flag= 2;
                        navController.navigate(R.id.action_nav_slideshow_to_nav_gallery);
                        break;
                    case R.id.nav_settings:
                        flag = 3;
                        break;
                }
            }
            invalidateOptionsMenu();
            navController.navigate(item.getItemId());
            binding.drawerLayout.close();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("TAG",item.getItemId()+"");

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(binding.navView.getCheckedItem().getItemId()==R.id.nav_gallery) {
            WebView view = findViewById(R.id.web);
            if(view.canGoBack()) view.goBack();
            else Navigation.findNavController(this,R.id.nav_host_fragment_content_main).navigateUp();
        } else super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(getLanguage(newBase));
    }
    private Context getLanguage(Context context) {
        Locale locale = new Locale(context.getSharedPreferences(MyApp.prefs,MODE_PRIVATE).getString(MyApp.language,"ru"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    public void setTitle(String s) {
        getSupportActionBar().setTitle(s);
    }
}