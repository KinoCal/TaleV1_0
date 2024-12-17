package com.example.talev1_0.helpers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarHelper {

    private final View rootView;
    private int gravity = Gravity.CENTER; // Default gravity
    private int backgroundColor = Color.DKGRAY; // Default background color
    private int titleTextColor = Color.WHITE; // Default title text color
    private int messageTextColor = Color.YELLOW; // Default message text color
    private float titleTextSize = 24f; // Title text size in SP
    private float messageTextSize = 24f; // Message text size in SP

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

    public SnackbarHelper setTitleTextColor(int color) {
        this.titleTextColor = color;
        return this;
    }

    public SnackbarHelper setMessageTextColor(int color) {
        this.messageTextColor = color;
        return this;
    }

    public SnackbarHelper setTitleTextSize(float size) {
        this.titleTextSize = size;
        return this;
    }

    public SnackbarHelper setMessageTextSize(float size) {
        this.messageTextSize = size;
        return this;
    }

    public void show(String title, String message) {
        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();

        // Customize position
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = gravity;
        snackbarView.setLayoutParams(params);

        // Set background color
        snackbarView.setBackgroundColor(backgroundColor);

        // Create a custom layout for the Snackbar content
        LinearLayout customLayout = new LinearLayout(snackbarView.getContext());
        customLayout.setOrientation(LinearLayout.VERTICAL);
        customLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Add title TextView
        TextView titleTextView = new TextView(snackbarView.getContext());
        titleTextView.setText(title);
        titleTextView.setTextColor(titleTextColor);
        titleTextView.setTextSize(titleTextSize);
        titleTextView.setGravity(Gravity.CENTER);
        customLayout.addView(titleTextView);

        // Add message TextView
        TextView messageTextView = new TextView(snackbarView.getContext());
        messageTextView.setText(message);
        messageTextView.setTextColor(messageTextColor);
        messageTextView.setTextSize(messageTextSize);
        messageTextView.setGravity(Gravity.CENTER);
        customLayout.addView(messageTextView);

        // Add the custom layout to the Snackbar
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(customLayout, 0);

        snackbar.show();
    }

    public void show(String title) {
        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();

        // Customize position
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = gravity;
        snackbarView.setLayoutParams(params);

        // Set background color
        snackbarView.setBackgroundColor(backgroundColor);

        // Create a custom layout for the Snackbar content
        LinearLayout customLayout = new LinearLayout(snackbarView.getContext());
        customLayout.setOrientation(LinearLayout.VERTICAL);
        customLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Add title TextView
        TextView titleTextView = new TextView(snackbarView.getContext());
        titleTextView.setText(title);
        titleTextView.setTextColor(titleTextColor);
        titleTextView.setTextSize(titleTextSize);
        titleTextView.setGravity(Gravity.CENTER);
        customLayout.addView(titleTextView);


        // Add the custom layout to the Snackbar
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(customLayout, 0);

        snackbar.show();
    }
}
