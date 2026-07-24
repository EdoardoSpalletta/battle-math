package com.battlemath.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.battlemath.R;
import com.battlemath.constants.ConstantBM;
import com.battlemath.constants.Messages;
import com.battlemath.controller.UiUtils;
import com.battlemath.model.MechModel;
import com.battlemath.model.TankModel;
import com.battlemath.utils.MechUtils;
import com.battlemath.utils.TankUtils;
import com.battlemath.utils.Utils;
import com.battlemath.utils.audio.SequentialSoundPlayer;
import com.battlemath.utils.audio.SoundManager;

public class ArmaPersonalizzataFragment extends Fragment {

    private static final String TAG = "ArmaPersonalizzataFragment";
    // --- UI Elements ---
    private EditText damageValue, groupingSize, inputClusterDmg, editTextNumberGroupingMod;
    private TextView textViewClusterMod;
    private TextView outputH, outputCT, outputLT, outputRT, outputLA, outputLL, outputRA, outputRL, outputCrit, outputCluster;
    private TextView outputFRTV, outputLSV, outputRSV, outputTUV, outputRRV, outputCritSideV, outputCritTuV, outputMotSysDmgV;
    private RadioGroup radioGroupSideHit, radioGroup2;
    private Button submitButton;
    private ImageView imageViewCleanInput, imageViewWeapons, imageViewMech, imageViewVehicle, imageViewHelperGrouping;
    private CheckBox checkBoxCluster;
    private FrameLayout frameLayoutMech, frameLayoutVehicle;
    // --- Logic / Sounds ---
    private SequentialSoundPlayer soundPlayer;

    public ArmaPersonalizzataFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_weapon, container, false);

        // --- Inizializzazione utilities ---
        Utils.initialize(requireContext());
        soundPlayer = SequentialSoundPlayer.getInstance(requireContext());
        SoundManager.setIsMuted(SoundManager.loadMuteState(requireContext()));

        // --- Recupero preferenze ---
        SharedPreferences prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        initViews(view);
        setupListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageViewWeapons.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SequentialSoundPlayer.getInstance(requireContext()).stopAndClear();
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPlayer.stopAndClear();
    }

    // ---------------------- INIZIALIZZAZIONE ----------------------
    private void initViews(View view) {
        // EditText
        damageValue = view.findViewById(R.id.editTextWeaponSize);
        groupingSize = view.findViewById(R.id.editTextNumberGrouping);
        inputClusterDmg = view.findViewById(R.id.editTextClusterDmg);
        editTextNumberGroupingMod = view.findViewById(R.id.editTextNumberGroupingMod);

        // TextView
        textViewClusterMod = view.findViewById(R.id.TextViewClusterMod);
        outputH = view.findViewById(R.id.textViewOutputHDmg);
        outputCT = view.findViewById(R.id.textViewOutputCTDmg);
        outputLT = view.findViewById(R.id.textViewOutputLTDmg);
        outputRT = view.findViewById(R.id.textViewOutputRTDmg);
        outputLA = view.findViewById(R.id.textViewOutputLADmg);
        outputLL = view.findViewById(R.id.textViewOutputLLDmg);
        outputRA = view.findViewById(R.id.textViewOutputRADmg);
        outputRL = view.findViewById(R.id.textViewOutputRLDmg);
        outputCrit = view.findViewById(R.id.textViewOutputCrit);
        outputCluster = view.findViewById(R.id.textViewOutputCluster);
        outputFRTV = view.findViewById(R.id.textViewOutputFRTdmg);
        outputRSV = view.findViewById(R.id.textViewOutputRSDmg);
        outputLSV = view.findViewById(R.id.textViewOutputLSDmg);
        outputTUV = view.findViewById(R.id.textViewOutputTUDmg);
        outputRRV = view.findViewById(R.id.textViewOutputRRSDmg);
        outputCritSideV = view.findViewById(R.id.textViewOutputCritV);
        outputCritTuV = view.findViewById(R.id.textViewOutputCritVTurret);
        outputMotSysDmgV = view.findViewById(R.id.textViewOutputCritVSysMotive);

        // RadioGroup
        radioGroupSideHit = view.findViewById(R.id.radioGroupSideHit);
        radioGroup2 = view.findViewById(R.id.radioGroup2);

        // Buttons & ImageViews
        submitButton = view.findViewById(R.id.submitButton);
        imageViewCleanInput = view.findViewById(R.id.imageViewCleanInput);
        imageViewHelperGrouping = view.findViewById(R.id.imageViewHelperGrouping);
        imageViewWeapons = view.findViewById(R.id.imageViewWeapons);
        imageViewMech = view.findViewById(R.id.imageViewMech);
        imageViewVehicle = view.findViewById(R.id.imageViewVehicle);

        // CheckBox
        checkBoxCluster = view.findViewById(R.id.checkBoxCluster);

        // FrameLayouts
        frameLayoutMech = view.findViewById(R.id.frameLayoutMech);
        frameLayoutVehicle = view.findViewById(R.id.frameLayoutVehicle);
    }

    // ---------------------- LISTENERS ----------------------
    private void setupListeners() {
        // Cluster Checkbox helper
        UiUtils.setupClusterCheckbox(checkBoxCluster, inputClusterDmg, editTextNumberGroupingMod, textViewClusterMod, imageViewHelperGrouping);
        // Submit button
        submitButton.setOnClickListener(v -> handleSubmit());
        // Pulizia input
        imageViewCleanInput.setOnClickListener(v -> cleanInput());
    }

    // ---------------------- SUBMIT ----------------------
    private void handleSubmit() {
        UiUtils.pauseButton(submitButton);
        UiUtils.hideKeyboard(requireActivity(), damageValue, groupingSize);

        if (!Utils.validateDamageInput(damageValue, groupingSize)) {
            UiUtils.generateToast(requireContext(), Messages.ERROR_INVALID_INPUT);
            return;
        }

        int damage = safeParseInt(damageValue);
        int grouping = safeParseInt(groupingSize);
        int damagePerShot = UiUtils.getClusterDamage(checkBoxCluster, inputClusterDmg, requireContext());
        int groupingModifier = UiUtils.parseGroupingModifier(editTextNumberGroupingMod);

        int idHitTable = validateRadioSelection(radioGroupSideHit, Messages.ERROR_SELECT_HIT_DIRECTION);
        if (idHitTable == -1) return;

        int mode = validateRadioSelection(radioGroup2, Messages.ERROR_SELECT_MODE);
        if (mode == -1) return;

        imageViewWeapons.setVisibility(View.GONE);

        // --- Riproduzione suoni ---
        if (!SoundManager.getIsMuted()) {
            SequentialSoundPlayer.getInstance(requireContext()).playRandomSound();
        }

        // --- Calcolo e visualizzazione ---
        switch (mode) {
            case ConstantBM.MODE_MECH:
                MechModel mechResult = MechUtils.handleMechMode(damage, grouping, idHitTable, damagePerShot, groupingModifier, checkBoxCluster.isChecked(), outputCluster);
                MechUtils.displayResultsMech(mechResult, idHitTable, frameLayoutMech, frameLayoutVehicle, imageViewMech, outputH, outputLT, outputRT, outputCT, outputLA, outputLL, outputRA, outputRL, outputCrit);
                break;
            case ConstantBM.MODE_TANK:
                TankModel tankResult = TankUtils.handleTankMode(damage, grouping, idHitTable, damagePerShot, groupingModifier, checkBoxCluster.isChecked(), outputCluster);
                TankUtils.displayResultsTank(tankResult, idHitTable, frameLayoutMech, frameLayoutVehicle, imageViewVehicle, outputFRTV, outputLSV, outputRSV, outputRRV, outputTUV, outputCritSideV, outputCritTuV, outputMotSysDmgV);
                break;
            default:
                UiUtils.generateToast(requireContext(), Messages.ERROR_INVALID_MODE);
        }
    }

    // ---------------------- UTILITY ----------------------
    private void cleanInput() {
        EditText[] edits = {damageValue, groupingSize, inputClusterDmg, editTextNumberGroupingMod};
        EditText[] editsGone = {inputClusterDmg,editTextNumberGroupingMod };
        TextView[] texts = {textViewClusterMod};
        CheckBox[] checks = {checkBoxCluster};
        RadioGroup[] radios = {radioGroupSideHit, radioGroup2};

        imageViewWeapons.setVisibility(View.VISIBLE);
        frameLayoutMech.setVisibility(View.GONE);
        frameLayoutVehicle.setVisibility(View.GONE);
        for (EditText e : edits) { e.setText(""); }
        for (EditText e : editsGone) { e.setVisibility(View.GONE); }
        for (TextView t : texts) t.setVisibility(View.GONE);
        for (CheckBox c : checks) c.setChecked(false);
        for (RadioGroup r : radios) r.clearCheck();
    }

    private int safeParseInt(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int validateRadioSelection(RadioGroup radioGroup, String errorMsg) {
        int id = UiUtils.getSelectedRadioButtonId(requireContext(), radioGroup, TAG);
        if (id == -1) UiUtils.generateToast(requireContext(), errorMsg);
        return id;
    }
}