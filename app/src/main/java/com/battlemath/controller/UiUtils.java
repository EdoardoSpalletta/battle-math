package com.battlemath.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.battlemath.ActivityWeaponList;
import com.battlemath.R;
import com.battlemath.constants.ConstantBM;
import com.battlemath.constants.Messages;
import com.battlemath.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UiUtils {

    //Set damage and visibility for the location
    public static void setDamageAndVisibility(int damage, TextView outputText) {
        outputText.setText(String.valueOf(damage));
        if (damage <= 0) {
            outputText.setVisibility(View.INVISIBLE);
        } else {
            outputText.setVisibility(View.VISIBLE);
        }
    }

    public static int getSelectedHitTable(String TAG, RadioButton selectedRB) {
        int idTable = -1;
        try {
            idTable = Integer.parseInt(selectedRB.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, Messages.ERROR_PARSING_RB_ID, e);
        }
        return idTable;
    }

    public static void pauseButton(Button button) {
        button.setEnabled(false);
        button.postDelayed(() -> button.setEnabled(true), 1000);
    }

    public static void pauseAllButtons(ViewGroup rootLayout, long delayMillis) {
        List<Button> buttonsToEnable = new ArrayList<>();
        for (int i = 0; i < rootLayout.getChildCount(); i++) {
            View child = rootLayout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (button.isEnabled()) {
                    button.setEnabled(false);
                    buttonsToEnable.add(button);
                }
            }
            rootLayout.postDelayed(() -> {
                for (Button button : buttonsToEnable) {
                    button.setEnabled(true);
                }
            }, delayMillis);
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void setPositionTextView(ImageView imageView, TextView textView, float relativeX, float relativeY) {
        imageView.post(() -> {

            RectF drawableRect = getImageBounds(imageView);
            int marginTop = ((FrameLayout.LayoutParams) imageView.getLayoutParams()).topMargin;
            float xPosition = drawableRect.left + relativeX * drawableRect.width();
            float yPosition = drawableRect.top + relativeY * drawableRect.height() + marginTop;
            textView.post(() -> {
                int textViewWidth = textView.getWidth();
                int textViewHeight = textView.getHeight();

                int centeredX = (int) (xPosition - (textViewWidth / 2f));
                int centeredY = (int) (yPosition - (textViewHeight / 2f));

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.leftMargin = centeredX;
                layoutParams.topMargin = centeredY;

                textView.setLayoutParams(layoutParams);
            });
        });
    }

    private static RectF getImageBounds(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        // Dimensioni originali dell'immagine
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        // Dimensioni visibili dell'ImageView
        int imageViewWidth = imageView.getWidth();
        int imageViewHeight = imageView.getHeight();
        // Scala per adattare l'immagine
        float scale = calculateImageScale(imageViewWidth, imageViewHeight, intrinsicWidth, intrinsicHeight);
        return calculateScaledImageBounds(scale, intrinsicWidth, intrinsicHeight, imageViewWidth, imageViewHeight);
    }

    private static float calculateImageScale(int imageViewWidth, int imageViewHeight, int intrinsicWidth, int intrinsicHeight) {
        float scaleX = (float) imageViewWidth / intrinsicWidth;
        float scaleY = (float) imageViewHeight / intrinsicHeight;
        return Math.min(scaleX, scaleY);  // Prendi il più piccolo dei due per mantenere le proporzioni
    }

    private static RectF calculateScaledImageBounds(float scale, int intrinsicWidth, int intrinsicHeight, int imageViewWidth, int imageViewHeight) {
        float scaledWidth = intrinsicWidth * scale;
        float scaledHeight = intrinsicHeight * scale;
        float left = (imageViewWidth - scaledWidth) / 2;
        float top = (imageViewHeight - scaledHeight) / 2;
        return new RectF(left, top, left + scaledWidth, top + scaledHeight);
    }

    public static void showDonationDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        Button btnDonate = dialogView.findViewById(R.id.btnDonate);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnDonate.setOnClickListener(v -> {
            Intent browserIntent = openPayPal();
            context.startActivity(browserIntent);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private static Intent openPayPal() {
        String paypalUrl = ConstantBM.PAYPAL_URL;
        return new Intent(Intent.ACTION_VIEW, Uri.parse(paypalUrl));
    }

    public static void setupClusterCheckbox(CheckBox checkBox, EditText inputClusterDmg, EditText groupingMod, TextView textViewClusterMod, ImageView imageViewHelperGrouping) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            inputClusterDmg.setEnabled(isChecked);
            inputClusterDmg.setText("");
            inputClusterDmg.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            groupingMod.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            groupingMod.setText("");
            textViewClusterMod.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            imageViewHelperGrouping.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    public static void hideKeyboard(Activity activity, View... views) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        for (View view : views) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getClusterDamage(CheckBox checkBox, EditText inputClusterDmg, Context context) {
        if (checkBox.isChecked()) {
            String clusterInput = inputClusterDmg.getText().toString();
            if (Utils.isNotEmpty(clusterInput)) {
                try {
                    return Integer.parseInt(clusterInput);
                } catch (NumberFormatException e) {
                    generateToast(context, Messages.ERROR_INPUT_CLUSTER);
                    return -1;
                }
            } else {
                generateToast(context, Messages.ERROR_INPUT_CLUSTER);
                return -1;
            }
        }
        return 1;
    }

    public static int parseGroupingModifier(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void navigateToWeaponList(Activity activity, ImageView imageViewWeapons) {
        imageViewWeapons.setVisibility(View.GONE);
        Intent intent = new Intent(activity, ActivityWeaponList.class);
        activity.startActivity(intent);
    }

    public static void toggleVisibility(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static int getSelectedRadioButtonId(Context context, RadioGroup selected, String tag) {
        int selectedId = selected.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRB = (RadioButton) ((Activity) context).findViewById(selectedId); // Usa findViewById con il contesto
            return getSelectedHitTable(tag, selectedRB);
        } else {
            return -1;
        }
    }

    public static void handleCriticals(int criticalCount, TextView outputCrit, String messagePrefix, String additionalMsg) {
        if (criticalCount > 0) {
            outputCrit.setVisibility(TextView.VISIBLE);
            String msgCrit = messagePrefix + criticalCount + additionalMsg;
            outputCrit.setText(msgCrit);
        } else {
            outputCrit.setVisibility(TextView.INVISIBLE);
        }
    }

    public static void generateToast(Context appContext, String message) {
        Toast toast = Toast.makeText(appContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -100);
        toast.show();
    }

}
