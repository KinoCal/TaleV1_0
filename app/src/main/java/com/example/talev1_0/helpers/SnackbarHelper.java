package com.example.talev1_0.helpers;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarHelper {

    private final View rootView;
    private int gravity = Gravity.CENTER; // Default gravity
    private int backgroundColor = Color.DKGRAY; // Default background color
    private int textColor = Color.WHITE; // Default text color

    public SnackbarHelper(View rootView) {
        this.rootView = rootView;
    }

    public SnackbarHelper setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public SnackbarHelper setBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public SnackbarHelper setTextColor(int color) {
        this.textColor = color;
        return this;
    }

    public void show(String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();

        // Customize position
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = gravity;
        snackbarView.setLayoutParams(params);

        // Customize appearance
        snackbar.setBackgroundTint(backgroundColor)
                .setTextColor(textColor);

        // Center the text within the Snackbar
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        if (textView != null) {
            textView.setGravity(Gravity.CENTER); // Center text horizontally and vertically
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // Ensures proper alignment on modern Android
        }
        snackbar.show();
    }
}

