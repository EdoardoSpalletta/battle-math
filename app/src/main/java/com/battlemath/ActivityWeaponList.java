package com.battlemath;

import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.FrameLayout;

import com.battlemath.utils.audio.SoundManager;
import com.battlemath.R;
import com.battlemath.constants.ConstantBM;
import com.battlemath.constants.Messages;
import com.battlemath.utils.MechUtils;
import com.battlemath.utils.TankUtils;
import com.battlemath.controller.UiUtils;
import com.battlemath.utils.Utils;
import com.battlemath.model.MechModel;
import com.battlemath.model.TankModel;

public class ActivityWeaponList extends AppCompatActivity {

    private static final String TAG = "ActivityWeaponList";
    private SoundPool soundPool;
    private EditText groupingModifier, lbxSize;
    private ImageView imageViewWeapons, imageViewMech, imageViewVehicle;
    private Button buttonSRM6, buttonSRM4, buttonSRM2, buttonLRM20, buttonLRM15, buttonLRM10, buttonLRM5, buttonLBX;
    private TextView outputH, outputCT, outputLT, outputRT, outputLA, outputLL, outputRA, outputRL, outputCrit, outputCluster;
    private TextView outputFRTV, outputLSV, outputRSV, outputTUV, outputRRV, outputCritSideV, outputCritTuV, outputMotSysDmgV;
    private RadioGroup radioGroup, radioGroup2;
    private FrameLayout frameLayoutMech, frameLayoutVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_list);
        soundPool = SoundManager.getSoundPool(this);
        SoundManager.loadMuteState(this);
        initViews();
        setupListeners();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundManager.releaseSoundPool();
    }

    private void initViews() {
        groupingModifier = findViewById(R.id.editTextNumberGrouping);
        lbxSize = findViewById(R.id.editTextLBXsize);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);
        imageViewWeapons = findViewById(R.id.imageViewWeapons);

        //Bottoni armi
        buttonSRM6 = findViewById(R.id.buttonSRM6);
        buttonSRM4 = findViewById(R.id.buttonSRM4);
        buttonSRM2 = findViewById(R.id.buttonSRM2);
        buttonLRM20 = findViewById(R.id.buttonLRM20);
        buttonLRM15 = findViewById(R.id.buttonLRM15);
        buttonLRM10 = findViewById(R.id.buttonLRM10);
        buttonLRM5 = findViewById(R.id.buttonLRM5);
        buttonLBX = findViewById(R.id.buttonLBX);
        imageViewMech = findViewById(R.id.imageViewMech);

        //Display Mech
        frameLayoutMech = findViewById(R.id.frameLayoutMech);
        outputH = findViewById(R.id.textViewOutputHDmg);
        outputCT = findViewById(R.id.textViewOutputCTDmg);
        outputLT = findViewById(R.id.textViewOutputLTDmg);
        outputRT = findViewById(R.id.textViewOutputRTDmg);
        outputLA = findViewById(R.id.textViewOutputLADmg);
        outputLL = findViewById(R.id.textViewOutputLLDmg);
        outputRA = findViewById(R.id.textViewOutputRADmg);
        outputRL = findViewById(R.id.textViewOutputRLDmg);
        outputCrit = findViewById(R.id.textViewOutputCrit);
        outputCluster = findViewById(R.id.textViewOutputCluster);

        //Inizializzo views per i veicoli
        frameLayoutVehicle = findViewById(R.id.frameLayoutVehicle);
        imageViewVehicle = findViewById(R.id.imageViewVehicle);
        outputFRTV = findViewById(R.id.textViewOutputFRTdmg);
        outputRSV = findViewById(R.id.textViewOutputRSDmg);
        outputLSV = findViewById(R.id.textViewOutputLSDmg);
        outputTUV = findViewById(R.id.textViewOutputTUDmg);
        outputRRV = findViewById(R.id.textViewOutputRRSDmg);
        outputCritSideV = findViewById(R.id.textViewOutputCritV);
        outputCritTuV = findViewById(R.id.textViewOutputCritVTurret);
        outputMotSysDmgV = findViewById(R.id.textViewOutputCritVSysMotive);
    }

    private void setupListeners() {
        buttonSRM6.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.srm6, 1);
            handleClickWeapon(6, 1, 2);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonSRM4.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.srm4, 1);
            handleClickWeapon(4, 1, 2);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonSRM2.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.srm2, 1);
            handleClickWeapon(2, 1, 2);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonLRM20.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.lrm20, 1);
            handleClickWeapon(20, 5, 1);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonLRM15.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.lrm15, 1);
            handleClickWeapon(15, 5, 1);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonLRM10.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.lrm10, 1);
            handleClickWeapon(10, 5, 1);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonLRM5.setOnClickListener(v -> {
            int soundId = soundPool.load(this, R.raw.lrm5, 1);
            handleClickWeapon(5, 5, 1);
            if (!SoundManager.getIsMuted())
                soundPool.play(soundId, 1, 1, 0, 0, 1);
        });
        buttonLBX.setOnClickListener(v -> handleClickLBX(buttonLBX));
    }

    private void handleClickLBX(Button buttonLBX) {
        if (Utils.isNotEmpty(lbxSize.getText().toString())) {
            try {
                int size = Integer.parseInt(lbxSize.getText().toString());
                size = Math.abs(size);
                if (size < 1000) {
                    int soundId = soundPool.load(this, R.raw.lbx, 1);
                    handleClickWeapon(size, 1, 1);
                    if (!SoundManager.getIsMuted())
                        soundPool.play(soundId, 1, 1, 0, 0, 1);
                } else {
                    UiUtils.pauseButton(buttonLBX);
                    UiUtils.generateToast(this, Messages.ERROR_LBX_SIZE);
                }
            } catch (NumberFormatException e) {
                UiUtils.pauseButton(buttonLBX);
                UiUtils.generateToast(this, Messages.ERROR_LBX_SIZE);
            }
        } else {
            if (lbxSize.getVisibility() == View.GONE) {
                lbxSize.setVisibility(View.VISIBLE);
            } else {
                UiUtils.pauseButton(buttonLBX);
                UiUtils.generateToast(this, Messages.ERROR_LBX_SIZE);
            }
        }
    }

    private void handleClickWeapon(int damage, int grouping, int damagePerShot) {
        UiUtils.hideKeyboard(this, groupingModifier);
        UiUtils.pauseAllButtons(findViewById(R.id.main), 1000);
        int clusterMod = 0;
        try {
            clusterMod = Integer.parseInt(groupingModifier.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Enter only numbers.", e);
        }
        int idHitTable = UiUtils.getSelectedRadioButtonId(this, radioGroup, TAG);
        int mode = UiUtils.getSelectedRadioButtonId(this, radioGroup2, TAG);

        if (idHitTable == -1) {
            UiUtils.generateToast(this, Messages.ERROR_SELECT_HIT_DIRECTION);
            return;
        }
        if (mode == -1) {
            UiUtils.generateToast(this, Messages.ERROR_SELECT_MODE);
            return;
        }
        imageViewWeapons.setVisibility(View.GONE);
        switch (mode) {
            case ConstantBM.MODE_MECH:
                MechModel mechResult = MechUtils.handleMechMode(damage, grouping, idHitTable, damagePerShot, clusterMod, true, outputCluster);
                MechUtils.displayResultsMech(mechResult, idHitTable, frameLayoutMech, frameLayoutVehicle, imageViewMech, outputH, outputLT, outputRT, outputCT, outputLA, outputLL, outputRA, outputRL, outputCrit);
                break;
            case ConstantBM.MODE_TANK:
                TankModel tankResult = TankUtils.handleTankMode(damage, grouping, idHitTable, damagePerShot, clusterMod, true, outputCluster);
                TankUtils.displayResultsTank(tankResult, idHitTable, frameLayoutMech, frameLayoutVehicle, imageViewVehicle, outputFRTV, outputLSV, outputRSV, outputRRV, outputTUV, outputCritSideV, outputCritTuV, outputMotSysDmgV);
                break;
            default:
                UiUtils.generateToast(this, Messages.ERROR_INVALID_MODE);
        }
    }
}