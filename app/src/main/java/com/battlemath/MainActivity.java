package com.battlemath;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.battlemath.R;
import com.battlemath.controller.UiUtils;
import com.battlemath.fragments.ArmaPersonalizzataFragment;
import com.battlemath.fragments.MissioneFragment;
import com.battlemath.fragments.WeaponListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Fragments
    private final Fragment missioneFragment = new MissioneFragment();
    private final Fragment weaponListFragment = new WeaponListFragment();
    private final Fragment armaPersonalizzataFragment = new ArmaPersonalizzataFragment();
    private Fragment activeFragment = missioneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Barra fissa
        // Barra fissa
        TextView titleView = findViewById(R.id.textView4);
        TextView authorView = findViewById(R.id.view3);
        ImageView imgCup = findViewById(R.id.buttonDonate);

        // Imposta autore fisso
        authorView.setText(getString(R.string.author));


        // Bottom navigation
        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Aggiungi tutti i fragment, mostra solo quello iniziale
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, armaPersonalizzataFragment, "ArmaPersonalizzata").hide(armaPersonalizzataFragment)
                .add(R.id.fragment_container, weaponListFragment, "Armi").hide(weaponListFragment)
                .add(R.id.fragment_container, missioneFragment, "Missione")
                .commit();

        activeFragment = missioneFragment; // già visibile

        imgCup.setOnClickListener(v -> UiUtils.showDonationDialog(v.getContext()));

        // Listener BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_missione) {
                showFragment(missioneFragment);
                return true;
            } else if (id == R.id.nav_armi) {
                showFragment(weaponListFragment);
                return true;
            } else if (id == R.id.nav_arma_personalizzata) {
                showFragment(armaPersonalizzataFragment);
                return true;
            }
            return false;
        });

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .commit();
        activeFragment = fragment;
    }
}
