package com.battlemath;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.battlemath.controller.UiUtils;
import com.battlemath.fragments.CustomWeaponFragment;
import com.battlemath.fragments.OperationFragment;
import com.battlemath.fragments.SettingsFragment;
import com.battlemath.fragments.WeaponListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Fragments
    private  Fragment operationFragment;
    private  Fragment weaponListFragment;
    private  Fragment customWeaponFragment;
    private  Fragment settingsFragment;
    private Fragment activeFragment ;
    private String activeTag = TAG_OPERATION;
    private static final String TAG_OPERATION = "OPERATION";
    private static final String TAG_WEAPONS = "WEAPONS";
    private static final String TAG_CUSTOM_WEAPON = "CUSTOM_WEAPON";
    private static final String TAG_SETTINGS = "SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup theme before creating UI
        SharedPreferences prefs =getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String theme =prefs.getString("theme", "system");
        switch(theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
                break;

            case "dark":
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
                break;

            default:
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        setContentView(R.layout.activity_main);

        // Top bar
        TextView titleView = findViewById(R.id.textView4);
        ImageView buttonSettings = findViewById(R.id.buttonSettings);
        ImageView imgCup = findViewById(R.id.buttonDonate);

        // Bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Init fragments if not present
        if (savedInstanceState == null) {
            operationFragment = new OperationFragment();
            weaponListFragment = new WeaponListFragment();
            customWeaponFragment = new CustomWeaponFragment();
            settingsFragment = new SettingsFragment();

            // Add all fragments, show only main screen
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, settingsFragment, TAG_SETTINGS).hide(settingsFragment)
                    .add(R.id.fragment_container, customWeaponFragment, TAG_CUSTOM_WEAPON).hide(customWeaponFragment)
                    .add(R.id.fragment_container, weaponListFragment, TAG_WEAPONS).hide(weaponListFragment)
                    .add(R.id.fragment_container, operationFragment, TAG_OPERATION)
                    .commit();
            activeFragment = operationFragment;

        } else {
            operationFragment =getSupportFragmentManager().findFragmentByTag(TAG_OPERATION);
            weaponListFragment =getSupportFragmentManager().findFragmentByTag(TAG_WEAPONS);
            customWeaponFragment = getSupportFragmentManager().findFragmentByTag(TAG_CUSTOM_WEAPON);
            settingsFragment = getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS);
            restoreActiveFragment();

        }

        // Listener donation button
        imgCup.setOnClickListener(v -> UiUtils.showDonationDialog(v.getContext()));

        // Listener BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_missione) {
                showFragment(operationFragment);
                return true;
            } else if (id == R.id.nav_armi) {
                showFragment(weaponListFragment);
                return true;
            } else if (id == R.id.nav_arma_personalizzata) {
                showFragment(customWeaponFragment);
                return true;
            }
            return false;
        });

        buttonSettings.setOnClickListener(v -> showFragment(settingsFragment));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();

        if (operationFragment != null) {
            transaction.hide(operationFragment);
        }

        if (weaponListFragment != null) {
            transaction.hide(weaponListFragment);
        }

        if (customWeaponFragment != null) {
            transaction.hide(customWeaponFragment);
        }

        if (settingsFragment != null) {
            transaction.hide(settingsFragment);
        }
        transaction.show(fragment);
        transaction.commit();

        activeFragment = fragment;
        activeTag = getFragmentTag(fragment);
    }

    private void restoreActiveFragment() {
        if (settingsFragment != null && settingsFragment.isVisible()) {
            activeFragment = settingsFragment;
            activeTag = TAG_SETTINGS;
        } else if (weaponListFragment != null && weaponListFragment.isVisible()) {
            activeFragment = weaponListFragment;
            activeTag = TAG_WEAPONS;
        } else if (customWeaponFragment != null && customWeaponFragment.isVisible()) {
            activeFragment = customWeaponFragment;
            activeTag = TAG_CUSTOM_WEAPON;
        } else {
            activeFragment = operationFragment;
            activeTag = TAG_OPERATION;
        }
    }

    private String getFragmentTag(Fragment fragment) {
        if (fragment == operationFragment)
            return TAG_OPERATION;
        if (fragment == weaponListFragment)
            return TAG_WEAPONS;
        if (fragment == customWeaponFragment)
            return TAG_CUSTOM_WEAPON;
        if (fragment == settingsFragment)
            return TAG_SETTINGS;
        return null;
    }
}
