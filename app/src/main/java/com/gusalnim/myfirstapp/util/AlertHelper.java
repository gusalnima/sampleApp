package com.gusalnim.myfirstapp.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gusalnim.myfirstapp.R;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by gusalnim on 26/04/2019.
 */

public class AlertHelper {
    private static AlertDialog oldAlertDialog;
    public static void showMessage(Activity activity, Bitmap icon, String message, Button... button) {
        show(activity, null, null, message, null, false, button);
    }
    public static void showMessage(Activity activity, Bitmap icon, String title, String message, Button... button) {
        show(activity, null, title, message, null, false, button);
    }

    public static void showMessage(Activity activity, Bitmap icon, String title, String message, boolean isRight, Button... button) {
        show(activity, null, title, message, null, isRight, button);
    }

    public static void showMessage(Activity activity, Bitmap icon, String title, String message, View innerView, Button... button) {
        show(activity, null, title, message, innerView, false, button);
    }

    public static AlertDialog show(Activity activity, Bitmap icon, String title, String message, View innerView, boolean isRight , Button... button) {
        if (null == activity || activity.isFinishing()) return null;

        View view = createViewGroup(activity, icon);

        if (null != title) {
            TextView txtTitle = ((TextView) view.findViewById(R.id.txtTitle));
            txtTitle.setText(title);
            txtTitle.setVisibility(View.VISIBLE);
            view.findViewById(R.id.viewDivider).setVisibility(View.VISIBLE);
        }

        if (null != message) {
            if(!isRight){
                TextView txtMessage = ((TextView) view.findViewById(R.id.txtMessage));
                txtMessage.setText(message);
                txtMessage.setVisibility(View.VISIBLE);
            } else {
                TextView txtMessage2 = ((TextView) view.findViewById(R.id.txtMessage2));
                txtMessage2.setText(message);
                txtMessage2.setVisibility(View.VISIBLE);
            }
        }

        if (null != innerView) {
            ViewGroup layoutInnerView = (ViewGroup) view.findViewById(R.id.layoutInnerView);
            layoutInnerView.addView(innerView);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        for (int index = 0; index < button.length; index++) {
            switch (index) {
                case 0:
                    setButton(alertDialog, button[index], (android.widget.Button) view.findViewById(R.id.btnAlpha));
                    break;

                case 1:
                    setButton(alertDialog, button[index], (android.widget.Button) view.findViewById(R.id.btnBeta));
                    break;

                case 2:
                    setButton(alertDialog, button[index], (android.widget.Button) view.findViewById(R.id.btnGamma));
                    LinearLayout layoutButton = (LinearLayout) view.findViewById(R.id.layoutButton);
                    layoutButton.setOrientation(LinearLayout.VERTICAL);
                    break;
            }
        }
        oldAlertDialog = alertDialog;
        alertDialog.show();

        return alertDialog;
    }

    private static View createViewGroup(Activity activity, Bitmap icon) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_basic, null);

        if (null != icon) ((ImageView) view.findViewById(R.id.imgIcon)).setImageBitmap(icon);
        else view.findViewById(R.id.imgIcon).setVisibility(View.GONE);

        return view;
    }

    private static void setButton(final AlertDialog alertDialog, final Button button, android.widget.Button buttonWidget) {
        buttonWidget.setVisibility(View.VISIBLE);
        buttonWidget.setText(button.getLabel());
        buttonWidget.setBackground(createStateListDrawable(button.getColor()));
        buttonWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != button.getOnClickListener()) button.getOnClickListener().onClick(alertDialog);
                else alertDialog.cancel();
            }
        });
    }

    private static StateListDrawable createStateListDrawable(int bgColor) {
        float[] hsv = new float[3];
        Color.colorToHSV(bgColor, hsv);
        hsv[2] *= 0.8f;
        int selectedColor = Color.HSVToColor(hsv);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(selectedColor));
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, new ColorDrawable(bgColor));
        return stateListDrawable;
    }

    public static class Button {
        private int color;
        private String label;
        private OnClickListener onClickListener;

        public Button(int color, String label) {
            this.color = color;
            this.label = label;
        }

        public Button(int color, String label, OnClickListener onClickListener) {
            this.color = color;
            this.label = label;
            this.onClickListener = onClickListener;
        }

        public int getColor() {
            return color;
        }

        public String getLabel() {
            return label;
        }

        public OnClickListener getOnClickListener() {
            return onClickListener;
        }
    }

    public static void alertHelperClose(){
        oldAlertDialog.cancel();
    }

    public interface OnClickListener {
        void onClick(AlertDialog alertDialog);
    }
}
