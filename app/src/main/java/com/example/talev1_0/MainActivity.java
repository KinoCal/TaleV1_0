package com.example.talev1_0;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.talev1_0.player.PlayerViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnMenuItemClickListener, FragmentChangeListener {

    // Define IDs for the fragment containers
    private static final int MAIN_FRAGMENT_CONTAINER_ID = R.id.main_fragment_container;
    private static final int PLAYER_INFO_FRAGMENT_CONTAINER_ID = R.id.player_info_fragment_container;
    private static final int MENU_FRAGMENT_CONTAINER_ID = R.id.menu_fragment_container;
    private static final int PLAYER_CHAT_WINDOW_CONTAINER_ID = R.id.player_chat_fragment_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for internet connectivity
        if (isInternetAvailable()) {
            Toast.makeText(this, "Internet is available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        // Load the ChatFragment
        /*getSupportFragmentManager().beginTransaction()
                .replace(PLAYER_CHAT_WINDOW_CONTAINER_ID, new ChatFragment())
                .commit();
*/
        // Load the MenuFragment
        getSupportFragmentManager().beginTransaction()
                .replace(MENU_FRAGMENT_CONTAINER_ID, new MenuFragment())
                .commit();

        // Load the PlayerInfoFragment
        getSupportFragmentManager().beginTransaction()
                .replace(PLAYER_INFO_FRAGMENT_CONTAINER_ID, new PlayerInfoFragment())
                .commit();

        // Load the default main fragment (e.g., MapFragment)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(MAIN_FRAGMENT_CONTAINER_ID, new InventoryFragment())
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePlayerData();
    }


    private void savePlayerData() {
        PlayerViewModel playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        playerViewModel.savePlayer();
    }


    @Override
    public void onMenuItemClicked(int itemId) {
        Fragment selectedFragment = null;

        if (itemId == R.id.nav_inventory) {

            selectedFragment = new InventoryFragment();
        } else if (itemId == R.id.nav_map) {

            selectedFragment = new MapFragment();
        } else if (itemId == R.id.nav_shop) {

            selectedFragment = new ShopFragment();
        }
        // Add more cases as needed

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(MAIN_FRAGMENT_CONTAINER_ID, selectedFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void onFragmentChange(String fragmentTag) {
        Fragment fragment;
        switch (fragmentTag) {
            case "BattleArea":
                fragment = new BattleAreaFragment();
                break;

            case "InventoryFragment":
                fragment = new InventoryFragment();
                break;

            case "MapFragment":
                fragment = new MapFragment();
                break;
            // Handle other fragment transitions if needed
            default:
                fragment = new MapFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Network network = connectivityManager.getActiveNetwork();
        if (network == null) {
            return false; // No active network
        }

        NetworkCapabilities capabilities =
                connectivityManager.getNetworkCapabilities(network);

        return capabilities != null &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
