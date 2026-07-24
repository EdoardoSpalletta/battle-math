package com.battlemath.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.battlemath.utils.audio.SequentialSoundPlayer;
import com.battlemath.utils.audio.SoundManager;
import com.battlemath.R;
import com.battlemath.constants.ConstantBM;
import com.battlemath.constants.Messages;
import com.battlemath.controller.UiUtils;
import com.battlemath.model.MechModel;
import com.battlemath.model.TankModel;
import com.battlemath.utils.MechUtils;
import com.battlemath.utils.TankUtils;
import com.battlemath.utils.Utils;

public class WeaponListFragment extends Fragment {

    private static final String TAG = "WeaponListFragment";
    private EditText groupingModifier, lbxSize;
    private ImageView  imageViewWeapons, imageViewMech, imageViewVehicle;
    private Button buttonSRM6, buttonSRM4, buttonSRM2, buttonLRM20, buttonLRM15, buttonLRM10, buttonLRM5, buttonLBX;
    private TextView outputH, outputCT, outputLT, outputRT, outputLA, outputLL, outputRA, outputRL, outputCrit, outputCluster;
    private TextView outputFRTV, outputLSV, outputRSV, outputTUV, outputRRV, outputCritSideV, outputCritTuV, outputMotSysDmgV;
    private RadioGroup radioGroup, radioGroup2;
    private FrameLayout frameLayoutMech, frameLayoutVehicle;
    private SequentialSoundPlayer soundPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weapon_list, container, false);
        soundPlayer = SequentialSoundPlayer.getInstance(requireContext());
        SoundManager.setIsMuted(SoundManager.loadMuteState(requireContext()));
        initViews(view);
        setupListeners();
        SharedPreferences prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onResume() {
        SoundManager.loadMuteState(requireContext());
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SequentialSoundPlayer.getInstance(this.requireContext()).stopAndClear();
    }

    private void initViews(View view) {
        groupingModifier = view.findViewById(R.id.editTextNumberGrouping);
        lbxSize = view.findViewById(R.id.editTextLBXsize);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup2 = view.findViewById(R.id.radioGroup2);
        imageViewWeapons = view.findViewById(R.id.imageViewWeapons);

        // Buttons armi
        buttonSRM6 = view.findViewById(R.id.buttonSRM6);
        buttonSRM4 = view.findViewById(R.id.buttonSRM4);
        buttonSRM2 = view.findViewById(R.id.buttonSRM2);
        buttonLRM20 = view.findViewById(R.id.buttonLRM20);
        buttonLRM15 = view.findViewById(R.id.buttonLRM15);
        buttonLRM10 = view.findViewById(R.id.buttonLRM10);
        buttonLRM5 = view.findViewById(R.id.buttonLRM5);
        buttonLBX = view.findViewById(R.id.buttonLBX);
        imageViewMech = view.findViewById(R.id.imageViewMech);

        // Display Mech
        frameLayoutMech = view.findViewById(R.id.frameLayoutMech);
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

        // Vehicle
        frameLayoutVehicle = view.findViewById(R.id.frameLayoutVehicle);
        imageViewVehicle = view.findViewById(R.id.imageViewVehicle);
        outputFRTV = view.findViewById(R.id.textViewOutputFRTdmg);
        outputRSV = view.findViewById(R.id.textViewOutputRSDmg);
        outputLSV = view.findViewById(R.id.textViewOutputLSDmg);
        outputTUV = view.findViewById(R.id.textViewOutputTUDmg);
        outputRRV = view.findViewById(R.id.textViewOutputRRSDmg);
        outputCritSideV = view.findViewById(R.id.textViewOutputCritV);
        outputCritTuV = view.findViewById(R.id.textViewOutputCritVTurret);
        outputMotSysDmgV = view.findViewById(R.id.textViewOutputCritVSysMotive);
    }

    private void setupListeners() {
        buttonSRM6.setOnClickListener(v -> handleClickWeapon(R.raw.srm6, 6, 1, 2));
        buttonSRM4.setOnClickListener(v -> handleClickWeapon(R.raw.srm4, 4, 1, 2));
        buttonSRM2.setOnClickListener(v -> handleClickWeapon(R.raw.srm2, 2, 1, 2));
        buttonLRM20.setOnClickListener(v -> handleClickWeapon(R.raw.lrm20, 20, 5, 1));
        buttonLRM15.setOnClickListener(v -> handleClickWeapon(R.raw.lrm15, 15, 5, 1));
        buttonLRM10.setOnClickListener(v -> handleClickWeapon(R.raw.lrm10, 10, 5, 1));
        buttonLRM5.setOnClickListener(v -> handleClickWeapon(R.raw.lrm5, 5, 5, 1));
        buttonLBX.setOnClickListener(v -> handleClickLBX(buttonLBX));
    }

    private int parseLBXSize() throws NumberFormatException {
        return Math.abs(Integer.parseInt(lbxSize.getText().toString()));
    }

    private void handleClickLBX(Button buttonLBX) {
        String lbxText = lbxSize.getText().toString();
        if (Utils.isNotEmpty(lbxText)) {
            try {
                int size = parseLBXSize();
                if (size >= 1000) {
                    throw new NumberFormatException();
                }
                handleClickWeapon(R.raw.lbx, size, 1, 1);
            } catch (NumberFormatException e) {
                UiUtils.pauseButton(buttonLBX);
                UiUtils.generateToast(requireContext(), Messages.ERROR_LBX_SIZE);
            }
        } else {
            if (lbxSize.getVisibility() == View.GONE) {
                lbxSize.setVisibility(View.VISIBLE);
            } else {
                UiUtils.pauseButton(buttonLBX);
                UiUtils.generateToast(requireContext(), Messages.ERROR_LBX_SIZE);
            }
        }
    }

    private void handleClickWeapon(int soundRes, int damage, int grouping, int damagePerShot) {
        UiUtils.hideKeyboard(requireActivity(), groupingModifier);
        UiUtils.pauseAllButtons(requireView().findViewById(R.id.main), 1000);
        int clusterMod = 0;
        try {
            clusterMod = Integer.parseInt(groupingModifier.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Enter only numbers.", e);
        }
        int idHitTable = UiUtils.getSelectedRadioButtonId(requireContext(), radioGroup, TAG);
        int mode = UiUtils.getSelectedRadioButtonId(requireContext(), radioGroup2, TAG);

        if (idHitTable == -1) {
            UiUtils.generateToast(requireContext(), Messages.ERROR_SELECT_HIT_DIRECTION);
            return;
        }
        if (mode == -1) {
            UiUtils.generateToast(requireContext(), Messages.ERROR_SELECT_MODE);
            return;
        }
        imageViewWeapons.setVisibility(View.GONE);
        switch (mode) {
            case ConstantBM.MODE_MECH:
                MechModel mechResult = MechUtils.handleMechMode(damage, grouping, idHitTable, damagePerShot, clusterMod, true, outputCluster);
                playWeaponSounds(soundRes, mechResult, null);
                MechUtils.displayResultsMech(mechResult, idHitTable, frameLayoutMech, frameLayoutVehicle, imageViewMech, outputH, outputLT, outputRT, outputCT, outputLA, outputLL, outputRA, outputRL, outputCrit);
                return;
            case ConstantBM.MODE_TANK:
                TankModel tankResult = TankUtils.handleTankMode(damage, grouping, idHitTable, damagePerShot, clusterMod, true, outputCluster);
                playWeaponSounds(soundRes, null, tankResult);
                TankUtils.displayResultsTank(tankResult, idHitTable, frameLayoutMech, frameLayoutVehicle, imageViewVehicle, outputFRTV, outputLSV, outputRSV, outputRRV, outputTUV, outputCritSideV, outputCritTuV, outputMotSysDmgV);
                return;
            default:
                UiUtils.generateToast(requireContext(), Messages.ERROR_INVALID_MODE);
                return;
        }
    }

    private void playWeaponSounds(int soundRes, MechModel mech, TankModel tank) {
        if (!SoundManager.getIsMuted()) {
            soundPlayer.enqueue(soundRes, 1.0f);
        }
    }
}
