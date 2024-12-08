package com.example.talev1_0;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Field;

public class MenuFragment extends Fragment {


    public interface OnMenuItemClickListener {
        void onMenuItemClicked(int itemId);
    }

    private OnMenuItemClickListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuItemClickListener) {
            callback = (OnMenuItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnMenuItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView currentTitle = view.findViewById(R.id.current_fragment_title);
        // Find the menu trigger view
        View menuTrigger = view.findViewById(R.id.menu_trigger);

        // Set up the PopupMenu
        menuTrigger.setOnClickListener(v -> {
            // Create PopupMenu
            Context wrapper = new ContextThemeWrapper(requireContext(), R.style.CustomPopupMenu);
            PopupMenu popupMenu = new PopupMenu(wrapper, menuTrigger);
            popupMenu.inflate(R.menu.dropdown_menu);

            // Handle menu item clicks
            popupMenu.setOnMenuItemClickListener(item -> {
                handleMenuSelection(item);
                return true;
            });

            // Access PopupWindow for positioning
            try {
                Field mPopupField = PopupMenu.class.getDeclaredField("mPopup");
                mPopupField.setAccessible(true);
                Object mPopup = mPopupField.get(popupMenu);
                if (mPopup instanceof android.widget.ListPopupWindow) {
                    ((android.widget.ListPopupWindow) mPopup).setHorizontalOffset(150); // Adjust X-axis offset
                    ((android.widget.ListPopupWindow) mPopup).setVerticalOffset(100);   // Adjust Y-axis offset
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Show PopupMenu
            popupMenu.show();
        });



    }

    public void updateCurrentTitle(String newTitle) {
        View view = getView(); // Get the root view of the fragment
        if (view != null) {
            TextView titleTextView = view.findViewById(R.id.current_fragment_title);
            titleTextView.setText(newTitle); // Update the text
        }
    }


    private void handleMenuSelection(MenuItem item) {
        if (callback != null) {
            callback.onMenuItemClicked(item.getItemId());
        }

        // Provide feedback for the selected menu item
        if (item.getItemId() == R.id.nav_inventory) {
            updateCurrentTitle("Inventory");
            //Toast.makeText(getContext(), "Inventory Selected", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_map) {
            updateCurrentTitle("Map");
            //Toast.makeText(getContext(), "Map Selected", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_shop) {
            updateCurrentTitle("Shop");
            //Toast.makeText(getContext(), "Shop Selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
