package com.battlemath.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.battlemath.R;
import com.battlemath.constants.ConstantBM;
import com.battlemath.utils.audio.SoundManager;

public class SettingsFragment extends Fragment {

    private SwitchCompat switchSound;
    private RadioGroup themeGroup;

    private RadioButton themeSystem;
    private RadioButton themeLight;
    private RadioButton themeDark;
    private TextView textAppVersion;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_settings,
                container,
                false);

        prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        initViews(view);
        setupListeners();
        return view;
    }


    private void initViews(View view) {

        switchSound = view.findViewById(R.id.switchSound);
        //Per gestire tema
        themeGroup = view.findViewById(R.id.themeGroup);
        themeSystem = view.findViewById(R.id.themeSystem);
        themeLight = view.findViewById(R.id.themeLight);
        themeDark = view.findViewById(R.id.themeDark);
        String theme = prefs.getString("theme", "system");
        textAppVersion = view.findViewById(R.id.textAppVersion);
        textAppVersion.setText( getString(R.string.version_format, ConstantBM.APP_VERSION) );

        switch (theme) {
            case "light":
                themeLight.setChecked(true);
                break;
            case "dark":
                themeDark.setChecked(true);
                break;
            default:
                themeSystem.setChecked(true);
                break;
        }

        // Stato audio corrente
        boolean muted = SoundManager.loadMuteState(requireContext());

        switchSound.setChecked(!muted);

    }


    private void setupListeners() {
        themeGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.themeSystem) {
                saveTheme("system");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

            }
            else if (checkedId == R.id.themeLight) {
                saveTheme("light");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            }
            else if (checkedId == R.id.themeDark) {
                saveTheme("dark");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

        });



        switchSound.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    boolean mute = !isChecked;
                    SoundManager.setIsMuted(mute);
                    SoundManager.saveMuteState(
                            requireContext(),
                            mute);
                });

    }

    private void saveTheme(String value) {
        prefs.edit().putString("theme", value).apply();
    }
}