package com.battlemath.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.battlemath.R;
import com.battlemath.constants.ConstantBM;
import com.battlemath.model.MissionViewModel;
import com.battlemath.utils.audio.SoundManager;

public class OperationFragment extends Fragment {

    private TextView textRound, textPvDef, textPvAtk, textViewVersion;
    private EditText editDefender, editAttacker;
    private ImageView imageViewCleanInput, imageView6;
    private MissionViewModel vm;
    private Button btnRoundMinus, btnRoundPlus, btnPvDefPlus, btnPvDefMinus, btnPvAtkPlus, btnPvAtkMinus;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        vm = new ViewModelProvider(requireActivity()).get(MissionViewModel.class);
        initViews(view);
        String version = textViewVersion.getText() + ConstantBM.APP_VERSION;
        textViewVersion.setText(version);
        setupListeners();
        boolean muted = SoundManager.loadMuteState(requireContext());
        SoundManager.setIsMuted(muted);
        return view;
    }

    private void setupListeners() {
        imageViewCleanInput.setOnClickListener((v -> cleanInput()));

        btnRoundMinus.setOnClickListener(v -> {
            if (vm.roundCount > 1) {
                vm.roundCount--;
                textRound.setText(String.valueOf(vm.roundCount));
            }
        });

        btnRoundPlus.setOnClickListener(v -> {
            vm.roundCount++;
            textRound.setText(String.valueOf(vm.roundCount));
        });

        // Listener per Punti Difensore
        btnPvDefMinus.setOnClickListener(v -> {
            if (vm.defPoints > 0) {
                vm.defPoints--;
                textPvDef.setText(String.valueOf(vm.defPoints));
            }
        });

        btnPvDefPlus.setOnClickListener(v -> {
            vm.defPoints++;
            textPvDef.setText(String.valueOf(vm.defPoints));
        });

        // Listener per Punti Attaccante
        btnPvAtkMinus.setOnClickListener(v -> {
            if (vm.atkPoints > 0) {
                vm.atkPoints--;
                textPvAtk.setText(String.valueOf(vm.atkPoints));
            }
        });

        btnPvAtkPlus.setOnClickListener(v -> {
            vm.atkPoints++;
            textPvAtk.setText(String.valueOf(vm.atkPoints));
        });
        imageView6.setImageResource(R.drawable.wallpaper);
    }

    private void initViews(View view) {
        // Riferimenti UI
        textRound = view.findViewById(R.id.turnNumValue);
        textPvDef = view.findViewById(R.id.textPvDef);
        textPvAtk = view.findViewById(R.id.textPvAtk);
        editDefender = view.findViewById(R.id.editDefender);
        editAttacker = view.findViewById(R.id.editAttacker);
        imageViewCleanInput = view.findViewById(R.id.imageViewCleanInput);
        imageView6 = view.findViewById(R.id.imageView6);
        imageView6.setImageResource(R.drawable.wallpaper);
        textViewVersion = view.findViewById(R.id.textViewVersion);
        imageView6.setImageResource(R.drawable.wallpaper);
        btnRoundMinus = view.findViewById(R.id.buttonMinusTurn);
        btnRoundPlus = view.findViewById(R.id.buttonPlusTurn);
        btnPvDefPlus = view.findViewById(R.id.vpDefPlus);
        btnPvDefMinus = view.findViewById(R.id.vpDefMinus);
        btnPvAtkPlus = view.findViewById(R.id.vpAtkPlus);
        btnPvAtkMinus = view.findViewById(R.id.vpAtkMinus);

        // Imposta i valori iniziali dal ViewModel
        textRound.setText(String.valueOf(vm.roundCount));
        textPvDef.setText(String.valueOf(vm.defPoints));
        textPvAtk.setText(String.valueOf(vm.atkPoints));
        editDefender.setText(vm.defenderName);
        editAttacker.setText(vm.attackerName);
    }


    private void cleanInput() {
        textRound.setText("1");
        textPvDef.setText("0");
        textPvAtk.setText("0");
        editDefender.setText("");
        editAttacker.setText("");
        vm.clearAll();
    }
}
