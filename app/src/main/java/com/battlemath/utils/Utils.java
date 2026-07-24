package com.battlemath.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.battlemath.controller.UiUtils;
import com.battlemath.constants.ConstantBM;
import com.battlemath.constants.Messages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.util.Random;

public class Utils {

    private static Context appContext;

    public static void initialize(Context context) {
        appContext = context.getApplicationContext();
    }

    private static final Random random = new Random();

    public static int roll2D6() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        Log.i("ROLL", "dice 1: " + dice1);
        Log.i("ROLL", "dice 2: " + dice2);
        return dice1 + dice2;
    }

    private static int calculateClusterResult(int roll, int clusterModifier) {
        int result = roll;
        result += clusterModifier;
        if (clusterModifier >= 0) {
            result = Math.min(12, result);
        } else {
            result = Math.max(2, result);
        }
        return result;
    }

    private static void displayClusterDamageMessage(TextView outputCluster, int roll, int result, int weaponDamage, int clusterModifier) {
        String msgCluster = Messages.MSG_CLUSTER_ROLL + (roll + (clusterModifier != 0 ? "+ mods= " + result : "")) + Messages.MSG_CLUSTER_HIT + weaponDamage;
        outputCluster.setText(msgCluster);
        outputCluster.setVisibility(View.VISIBLE);
    }

    public static int damageFromClusterWeapon(int weaponSize, TextView outputCluster, int clusterModifier) {
        int roll = roll2D6();
        int result = calculateClusterResult(roll, clusterModifier);

        try {
            // Carica il file Excel dalla cartella assets
            InputStream inputStream = appContext.getAssets().open(ConstantBM.TAB_CLUSTER_HITS);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Row rowWeaponSize = sheet.getRow(0);
            int indexWeaponSize = -1;
            boolean isWeaponSizeOk = false;
            //Controllo che la weapon size esista
            for (Cell cell : rowWeaponSize) {
                if (cell.getNumericCellValue() == weaponSize) {
                    isWeaponSizeOk = true;
                    indexWeaponSize = cell.getColumnIndex();
                    break;
                }
            }
            //Weapon size non supportata, restituisco un warning e salto il calcolo del danno cluster
            if (isWeaponSizeOk) {
                //calcolo il danno cluster
                Double danno = sheet.getRow(result - 1).getCell(indexWeaponSize).getNumericCellValue();
                weaponSize = danno.intValue();
                displayClusterDamageMessage(outputCluster, roll, result, weaponSize, clusterModifier);

            } else {
                UiUtils.generateToast(appContext, Messages.MSG_WEAPON_SIZE_NF);
                outputCluster.setText("");
                outputCluster.setVisibility(View.INVISIBLE);
            }
            workbook.close();
            return weaponSize;
        } catch (Exception e) {
            Log.e("ExcelReader", Messages.ERROR_PARSE_XLS, e);
        }
        return weaponSize;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean validateDamageInput(EditText damageInput, EditText groupingInput) {
        String damage = damageInput.getText().toString();
        String grouping = groupingInput.getText().toString();
        return isNotEmpty(damage) && isNotEmpty(grouping);
    }
}