package com.battlemath.utils;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.battlemath.constants.ConstantBM;
import com.battlemath.constants.Messages;
import com.battlemath.controller.UiUtils;
import com.battlemath.model.HitLocationTablesMech;
import com.battlemath.model.MechModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MechUtils {

    private static final Logger log = LogManager.getLogger(MechUtils.class);

    public static MechModel warTaxesMech(int damage, int grouping, int idHitTable, boolean clusterWeapon, TextView outputCluster, int clusterModifier) {
        MechModel mechModel = new MechModel();
        //assigning the damage grouping N times
        if (clusterWeapon) {
            damage = Utils.damageFromClusterWeapon(damage, outputCluster, clusterModifier);
        } else {
            outputCluster.setVisibility(View.INVISIBLE);
            outputCluster.setText("");
        }
        applyDamageMech(mechModel, damage / grouping, grouping, idHitTable);

        // assigning the remaining damage
        int resto = damage % grouping;
        if (resto > 0) {
            applyDamageMech(mechModel, 1, resto, idHitTable);
        }
        return mechModel;
    }

    public static void applyDamageMech(MechModel mechModel, int iterations, int damage, int idHitTable) {
        for (int i = 0; i < iterations; i++) {
            int hitLocationRoll = Utils.roll2D6();
            if (isCriticalHitMech(hitLocationRoll)) {
                mechModel.setCriticalCount(1 + mechModel.getCriticalCount());
            }
            assignDamageByIdTableMech(idHitTable, mechModel, damage, hitLocationRoll);
        }
    }

    //Through Armor Critical Hit
    public static boolean isCriticalHitMech(int toHit) {
        return toHit == 2;
    }

    private static void assignDamageByIdTableMech(int idHitTable, MechModel mechModel, int damage, int toHit) {
        switch (idHitTable) {
            case ConstantBM.ID_TABLE_LEFT:
                //LEFT
                mechModel.setDamage(HitLocationTablesMech.getPositionLeft(toHit), damage);
                break;
            case ConstantBM.ID_TABLE_FRONT:
            case ConstantBM.ID_TABLE_REAR:
                //FRONT or REAR
                mechModel.setDamage(HitLocationTablesMech.getPositionFront(toHit), damage);
                break;
            case ConstantBM.ID_TABLE_RIGHT:
                //RIGHT
                mechModel.setDamage(HitLocationTablesMech.getPositionRight(toHit), damage);
                break;
            default:
                log.error(Messages.ERROR_IDTABLE_NF);
                break;
        }
    }

    public static String getCritLocationMech(int hitTableId) {
        switch (hitTableId) {
            case 1:
                return Messages.MSG_LT;
            case 3:
                return Messages.MSG_RT;
            default:
                return Messages.MSG_CT;
        }
    }

    public static MechModel handleMechMode(int damage, int grouping, int idHitTable, int damagePerShot, int clusterModifier, boolean isClusterWeapon, TextView outputCluster) {
        MechModel resultMech = MechUtils.warTaxesMech(damage, grouping, idHitTable, isClusterWeapon, outputCluster, clusterModifier);
        if (damagePerShot > 1) {
            resultMech.multiplyDamageByShotSize(damagePerShot);
        }
        return resultMech;
    }

    public static void positionTextboxOnMechImage(ImageView imageView, TextView outputH, TextView outputCT, TextView outputLT, TextView outputRT, TextView outputLA, TextView outputLL, TextView outputRA, TextView outputRL) {
        UiUtils.setPositionTextView(imageView, outputH, ConstantBM.CENTER, ConstantBM.HEAD_Y);
        UiUtils.setPositionTextView(imageView, outputCT, ConstantBM.CENTER, ConstantBM.CT_Y);
        // LEFT
        UiUtils.setPositionTextView(imageView, outputLT, ConstantBM.LEFT_TORSO_X, ConstantBM.SIDE_TORSO_Y);
        UiUtils.setPositionTextView(imageView, outputLA, ConstantBM.LEFT_ARM_X, ConstantBM.ARM_Y);
        UiUtils.setPositionTextView(imageView, outputLL, ConstantBM.LEFT_LEG_X, ConstantBM.LEG_Y);
        // RIGHT
        UiUtils.setPositionTextView(imageView, outputRT, ConstantBM.RIGHT_TORSO_X, ConstantBM.SIDE_TORSO_Y);
        UiUtils.setPositionTextView(imageView, outputRA, ConstantBM.RIGHT_ARM_X, ConstantBM.ARM_Y);
        UiUtils.setPositionTextView(imageView, outputRL, ConstantBM.RIGHT_LEG_X, ConstantBM.LEG_Y);
    }

    public static void displayResultsMech(MechModel result, int hitTableId, FrameLayout frameLayoutMech, FrameLayout frameLayoutVehicle, ImageView imageViewMech,
                                          TextView outputH, TextView outputLT, TextView outputRT, TextView outputCT, TextView outputLA, TextView outputLL,
                                          TextView outputRA, TextView outputRL, TextView outputCrit) {

        // Gestisci visibilità
        frameLayoutVehicle.setVisibility(View.GONE);
        frameLayoutMech.setVisibility(View.VISIBLE);
        // Imposta danni e visibilità per ogni parte del Mech
        frameLayoutMech.setVisibility(View.VISIBLE);
        UiUtils.setDamageAndVisibility(result.getHead(), outputH);
        UiUtils.setDamageAndVisibility(result.getLeftTorso(), outputLT);
        UiUtils.setDamageAndVisibility(result.getRightTorso(), outputRT);
        UiUtils.setDamageAndVisibility(result.getCentreTorso(), outputCT);
        UiUtils.setDamageAndVisibility(result.getLeftArm(), outputLA);
        UiUtils.setDamageAndVisibility(result.getLeftLeg(), outputLL);
        UiUtils.setDamageAndVisibility(result.getRightArm(), outputRA);
        UiUtils.setDamageAndVisibility(result.getRightLeg(), outputRL);
        positionTextboxOnMechImage(imageViewMech, outputH, outputCT, outputLT, outputRT, outputLA, outputLL, outputRA, outputRL);
        UiUtils.handleCriticals(result.getCriticalCount(), outputCrit, Messages.MSG_TAC_MECH, MechUtils.getCritLocationMech(hitTableId));
        // Posiziona i textbox sulle immagini del Mech
        positionTextboxOnMechImage(imageViewMech, outputH, outputCT, outputLT, outputRT, outputLA, outputLL, outputRA, outputRL);
        // Gestione dei critici
        UiUtils.handleCriticals(result.getCriticalCount(), outputCrit, Messages.MSG_TAC_MECH, MechUtils.getCritLocationMech(hitTableId));
    }
}
