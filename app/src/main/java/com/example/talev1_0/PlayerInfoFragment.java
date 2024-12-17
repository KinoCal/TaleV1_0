package com.example.talev1_0;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.talev1_0.player.PlayerViewModel;

public class PlayerInfoFragment extends Fragment {
    private PlayerViewModel playerViewModel;
    private TextView textViewHp, textViewGold, textViewLevel, textViewExp;
    private ProgressBar playerHpProgressBar, playerExpProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_info, container, false);
        textViewHp = view.findViewById(R.id.player_hp_textview);
        textViewGold = view.findViewById(R.id.player_gold_textview);
        textViewLevel = view.findViewById(R.id.player_level_textview);
        textViewExp = view.findViewById(R.id.player_current_exp_textview);
        playerHpProgressBar = view.findViewById(R.id.player_hp_progressbar);
        playerExpProgressBar = view.findViewById(R.id.player_exp_progressbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel shared across the activity
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);


        // Observe the Player object
        playerViewModel.getPlayerLiveData().observe(getViewLifecycleOwner(), player -> {
            // Update UI when the Player object changes
            textViewHp.setText("HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
            textViewLevel.setText("Lvl: " + player.getLevel());
            textViewExp.setText("Exp: " + player.getCurrentExp() + "/" + player.getMaxExp());
            textViewGold.setText("Gold: " + player.getGold());
            playerHpProgressBar.setMax(player.getMaxHp());
            playerHpProgressBar.setProgress(player.getCurrentHp());

            playerExpProgressBar.setMax(player.getMaxExp());
            playerExpProgressBar.setProgress(player.getCurrentExp());
        });
    }

}
