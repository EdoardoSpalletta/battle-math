package com.battlemath.utils;

import android.view.View;
import android.widget.TextView;

import com.battlemath.constants.ConstantBM;
import com.battlemath.controller.UiUtils;
import com.battlemath.model.TankModel;
import com.battlemath.constants.Messages;
import com.battlemath.model.HitLocationTablesTank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class TankUtils {

    private static final Logger log = LogManager.getLogger(TankUtils.class);

    public static TankModel warTaxesTank(int damage, int grouping, int idHitTable, boolean clusterWeapon, TextView outputCluster, int clusterModifier) {
        TankModel tankModel = new TankModel();
        //assigning the damage grouping N times
        if (clusterWeapon ) {
            damage = Utils.damageFromClusterWeapon(damage, outputCluster, clusterModifier);
        } else {
            outputCluster.setVisibility(View.INVISIBLE);
            outputCluster.setText("");
        }
        applyDamageTank(tankModel, damage / grouping, grouping, idHitTable);
        // assigning the remaining damage
        int resto = damage % grouping;
        if (resto > 0) {
            applyDamageTank(tankModel, 1, resto, idHitTable);
        }
        return tankModel;
    }

    private static void applyDamageTank(TankModel tankModel, int iterations, int damage, int idHitTable) {
        for (int i = 0; i < iterations; i++) {
            int hitLocationRoll = Utils.roll2D6();
            assignCriticalHitOnTank(hitLocationRoll,tankModel,idHitTable);
            assignDamageByIdTableTank(idHitTable, tankModel, damage, hitLocationRoll);
        }
    }

    private static void assignCriticalHitOnTank(int toHit, TankModel tankModel, int idHitTable) {
        switch (toHit) {
            case 2:
                tankModel.setCriticalCount(1+tankModel.getCriticalCount());
                break;
            case 3:
            case 4:
            case 5:
            case 9:
                tankModel.setMotiveSysDmg(1+tankModel.getMotiveSysDmg());
                break;
            case 8:
                if (idHitTable== ConstantBM.ID_TABLE_LEFT || idHitTable== ConstantBM.ID_TABLE_RIGHT) {
                    tankModel.setCriticalCount(1+tankModel.getCriticalCount());
                }
                break;
            case 12:
                tankModel.setCriticalCountTu(1+tankModel.getCriticalCountTu());
                break;
        }
    }

    private static void assignDamageByIdTableTank(int idHitTable, TankModel tankModel, int damage, int toHit) {
        switch (idHitTable) {
            case ConstantBM.ID_TABLE_LEFT:
                //LEFT
                tankModel.setDamage(HitLocationTablesTank.getPositionLeft(toHit), damage);
                break;
            case ConstantBM.ID_TABLE_FRONT:
                tankModel.setDamage(HitLocationTablesTank.getPositionFront(toHit), damage);
                break;
            case ConstantBM.ID_TABLE_RIGHT:
                //RIGHT
                tankModel.setDamage(HitLocationTablesTank.getPositionRight(toHit), damage);
                break;
            case ConstantBM.ID_TABLE_REAR:
                //REAR
                tankModel.setDamage(HitLocationTablesTank.getPositionRear(toHit), damage);
                break;
            default:
                log.error(Messages.ERROR_IDTABLE_NF);
                break;
        }
    }

    public static String getCritLocationTank(int hitTableId) {
        switch (hitTableId) {
            case 1:
            case 3:
                return Messages.MSG_S;
            case 4:
                return Messages.MSG_R;
            default:
                return Messages.MSG_F;
        }
    }

    public static TankModel handleTankMode(int damage, int grouping, int idHitTable, int damagePerShot, int clusterModifier, boolean isClusterWeapon, TextView outputCluster) {
        TankModel resultTank = TankUtils.warTaxesTank(damage, grouping, idHitTable, isClusterWeapon, outputCluster, clusterModifier);
        if (damagePerShot > 1) {
            resultTank.multiplyDamageByShotSize(damagePerShot);
        }
        return resultTank;
    }

    // Metodo per visualizzare i risultati per Tank
    public static void displayResultsTank(TankModel result, int hitTableId,
                                          FrameLayout frameLayoutMech, FrameLayout frameLayoutVehicle,
                                          ImageView imageViewVehicle, TextView outputFRTV, TextView outputLSV,
                                          TextView outputRSV, TextView outputRRV, TextView outputTUV,
                                          TextView outputCritSideV, TextView outputCritTuV,
                                          TextView outputMotSysDmgV) {

        // Gestisci visibilità
        frameLayoutMech.setVisibility(View.GONE);
        frameLayoutVehicle.setVisibility(View.VISIBLE);

        // Imposta danni e visibilità per ogni parte del veicolo
        UiUtils.setDamageAndVisibility(result.getFront(), outputFRTV);
        UiUtils.setDamageAndVisibility(result.getLeft(), outputLSV);
        UiUtils.setDamageAndVisibility(result.getRight(), outputRSV);
        UiUtils.setDamageAndVisibility(result.getRear(), outputRRV);
        UiUtils.setDamageAndVisibility(result.getTurret(), outputTUV);

        // Posiziona i textbox sulle immagini del veicolo
        positionTextboxOnVehicleImage(imageViewVehicle, outputFRTV, outputLSV, outputRSV, outputRRV, outputTUV);

        // Gestione dei critici per il lato del veicolo
        UiUtils.handleCriticals(result.getCriticalCount(), outputCritSideV, Messages.MSG_CRT_SIDE_TANK, TankUtils.getCritLocationTank(hitTableId));
        // Gestione dei critici per la torretta
        UiUtils.handleCriticals(result.getCriticalCountTu(), outputCritTuV, Messages.MSG_CRT_TU_TANK, "");
        // Gestione dei danni del sistema motore
        UiUtils.handleCriticals(result.getMotiveSysDmg(), outputMotSysDmgV, Messages.MSG_CRT_MOT_SYS, "");
    }

    public static void positionTextboxOnVehicleImage(ImageView imageView, TextView outputFRTV, TextView outputLSV, TextView outputRSV, TextView outputRRV, TextView outputTUV) {
        UiUtils.setPositionTextView(imageView, outputFRTV, ConstantBM.FRONT_X, ConstantBM.CENTER);
        UiUtils.setPositionTextView(imageView, outputRSV, ConstantBM.SIDE_X, ConstantBM.RIGHT_SIDE_Y);
        UiUtils.setPositionTextView(imageView, outputLSV, ConstantBM.SIDE_X, ConstantBM.LEFT_SIDE_Y);
        UiUtils.setPositionTextView(imageView, outputRRV, ConstantBM.REAR_X, ConstantBM.CENTER);
        UiUtils.setPositionTextView(imageView, outputTUV, ConstantBM.TURRET_X, ConstantBM.CENTER);
    }

}
