package com.example.talev1_0;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.talev1_0.Factories.ItemFactories.MonsterFactory;
import com.example.talev1_0.handlers.FightHandler;
import com.example.talev1_0.helpers.SnackbarHelper;
import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.player.MonsterViewModel;
import com.example.talev1_0.player.PlayerViewModel;

public class BattleAreaFragment extends Fragment {
    private FragmentChangeListener fragmentChangeListener;
    private MonsterViewModel monsterViewModel;
    private TextView monsterNameTextView;
    private TextView monsterHpTextView;
    private TextView monsterDamageTextView;
    private TextView playerDamageTextView;
    private Button leaveButton;
    private PlayerViewModel playerViewModel;
    private BaseMonster currentMonster;
    private MonsterFactory monsterFactory;
    private FightHandler fightHandler;

    private ProgressBar monsterHpProgressBar;
    private ProgressBar playerAttackSpeedProgressBar, monsterAttackSpeedProgressBar;

    private Handler playerAttackHandler;
    private Handler playerAttackSpeedHandler = new Handler();
    private Handler monsterAttackHandler;
    private Handler monsterAttackSpeedHandler = new Handler();

    private Runnable playerAttackRunnable;
    private Runnable playerAttackSpeedRunnable;
    private Runnable monsterAttackRunnable;
    private Runnable monsterAttackSpeedRunnable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentChangeListener) {
            fragmentChangeListener = (FragmentChangeListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement FragmentChangeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle_area, container, false);

        playerDamageTextView = view.findViewById(R.id.player_damage_textview);
        playerAttackSpeedProgressBar = view.findViewById(R.id.player_attack_speed_progressbar);

        monsterDamageTextView = view.findViewById(R.id.monster_damage_textview);
        monsterAttackSpeedProgressBar = view.findViewById(R.id.monster_attack_speed_progressbar);

        monsterNameTextView = view.findViewById(R.id.monsterName);
        monsterHpTextView = view.findViewById(R.id.monsterHp);
        monsterHpProgressBar = view.findViewById(R.id.monster_hp_progressbar);

        leaveButton = view.findViewById(R.id.leave_button);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModels and handlers
        monsterViewModel = new ViewModelProvider(this).get(MonsterViewModel.class);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        fightHandler = new FightHandler();
        monsterFactory = new MonsterFactory();

        playerAttackHandler = new Handler();
        monsterAttackHandler = new Handler();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Observe monster data
        monsterViewModel.getMonster().observe(getViewLifecycleOwner(), monster -> {
            if (monster != null) {

                monsterNameTextView.setText(monster.getName() + " Lvl: " + monster.getLevel());
                monsterHpTextView.setText("Hp: " + monster.getHpValue() + "/" + monster.getMaxHpValue());
                monsterHpProgressBar.setMax(monster.getMaxHpValue());
                monsterHpProgressBar.setProgress(monster.getHpValue());

                monsterDamageTextView.setText("Monster Damage: " + monster.getDamageDealt());
                playerDamageTextView.setText("Player  Damage: " + playerViewModel.getPlayer().getDamageDealt());
            }
        });
        // Initialize the current monster using playerViewModel
        String enemyName = playerViewModel.getEnemyName();
        if (enemyName != null) {
            currentMonster = monsterFactory.createMonster(enemyName);
            monsterViewModel.setMonster(currentMonster);
        } else {
            currentMonster = null;
        }

        leaveButton.setOnClickListener(v -> {
            if (fragmentChangeListener != null) {
                fragmentChangeListener.onFragmentChange("MapFragment");
            }
        });

        // Start player and monster attack timers
        startPlayerAttackTimer(view);
        startMonsterAttackTimer();
    }

    private void startPlayerAttackTimer(View rootView) {
        if (playerAttackRunnable == null) {
            playerAttackRunnable = new Runnable() {
                @Override
                public void run() {
                    // Execute player's attack
                    if (fightHandler.attackMonster(rootView, playerViewModel, monsterViewModel)){
                    // Restart progress animation
                        stopPlayerAttackSpeedProgress();
                        startPlayerAttackSpeedProgress();


                    // Schedule the next attack based on the player's attack speed
                    double attackSpeed = playerViewModel.getPlayer().getAttackSpeed(); // Seconds per attack
                    long delay = (long) (1000 * attackSpeed); // Convert to milliseconds
                    playerAttackHandler.postDelayed(this, delay);

                    } else {
                    // Stop any ongoing timers
                    stopPlayerAttackTimer();
                    // Restart the player attack timer
                    startPlayerAttackTimer(rootView);
                    }
                }
            };
        }

        // Initial delay for player's first attack
        long initialDelay = (long) (1000 * playerViewModel.getPlayer().getAttackSpeed());
        playerAttackHandler.postDelayed(playerAttackRunnable, initialDelay);

        // Start progress animation
        startPlayerAttackSpeedProgress();
    }


    private void startPlayerAttackSpeedProgress() {
        playerAttackSpeedProgressBar.setProgress(0); // Reset progress to 0
        final int maxProgress = playerAttackSpeedProgressBar.getMax(); // Maximum value of the ProgressBar
        final double attackSpeed = playerViewModel.getPlayer().getAttackSpeed(); // Seconds per attack
        final long totalDuration = (long) (1000 * attackSpeed); // Convert to milliseconds
        final long updateInterval = 50; // Update progress every 50ms

        playerAttackSpeedRunnable = new Runnable() {
            private long elapsedTime = 0;

            @Override
            public void run() {
                elapsedTime += updateInterval;
                int progress = (int) ((elapsedTime * maxProgress) / totalDuration);
                playerAttackSpeedProgressBar.setProgress(progress);

                if (elapsedTime < totalDuration) {
                    // Continue updating the progress
                    playerAttackSpeedHandler.postDelayed(this, updateInterval);
                } else {
                    // Ensure the progress bar reaches its maximum value
                    playerAttackSpeedProgressBar.setProgress(maxProgress);
                }
            }
        };

        // Start the progress animation
        playerAttackSpeedHandler.postDelayed(playerAttackSpeedRunnable, updateInterval);
    }


    private void startMonsterAttackTimer() {
        if (monsterAttackRunnable == null) {
            monsterAttackRunnable = new Runnable() {
                @Override
                public void run() {
                    // Execute the monster's attack
                    if (fightHandler.attackPlayer(playerViewModel, monsterViewModel)) {
                        // Restart progress animation
                        stopMonsterAttackSpeedProgress();
                        startMonsterAttackSpeedProgress();

                        // Schedule the next attack based on the monster's attack speed
                        double monsterAttackSpeed = currentMonster.getAttackSpeed(); // Seconds per attack
                        long delay = (long) (1000 * monsterAttackSpeed); // Convert to milliseconds
                        monsterAttackHandler.postDelayed(this, delay);
                    } else {
                        SnackbarHelper snackbarHelper = new SnackbarHelper(getView());
                        snackbarHelper.setGravity(Gravity.BOTTOM).show("You have been slain");
                        if (fragmentChangeListener != null) {
                            fragmentChangeListener.onFragmentChange("InventoryFragment");
                        }
                    }
                }
            };
        }

        // Initial delay for monster's first attack
        long initialDelay = (long) (1000 * currentMonster.getAttackSpeed());
        monsterAttackHandler.postDelayed(monsterAttackRunnable, initialDelay);

        // Start progress animation
        startMonsterAttackSpeedProgress();
    }


    private void startMonsterAttackSpeedProgress() {
        monsterAttackSpeedProgressBar.setProgress(0); // Reset progress to 0
        final int maxProgress = monsterAttackSpeedProgressBar.getMax(); // Maximum value of the ProgressBar
        final double attackSpeed = currentMonster.getAttackSpeed(); // Seconds per attack
        final long totalDuration = (long) (1000 * attackSpeed); // Convert to milliseconds
        final long updateInterval = 50; // Update progress every 50ms

        monsterAttackSpeedRunnable = new Runnable() {
            private long elapsedTime = 0;

            @Override
            public void run() {
                elapsedTime += updateInterval;
                int progress = (int) ((elapsedTime * maxProgress) / totalDuration);
                monsterAttackSpeedProgressBar.setProgress(progress);

                if (elapsedTime < totalDuration) {
                    // Continue updating the progress
                    monsterAttackSpeedHandler.postDelayed(this, updateInterval);
                } else {
                    // Ensure the progress bar reaches its maximum value
                    monsterAttackSpeedProgressBar.setProgress(maxProgress);
                }
            }
        };

        // Start the progress animation
        monsterAttackSpeedHandler.postDelayed(monsterAttackSpeedRunnable, updateInterval);
    }


    private void stopPlayerAttackTimer() {
        if (playerAttackRunnable != null) {
            playerAttackHandler.removeCallbacks(playerAttackRunnable);
        }
    }

    private void stopPlayerAttackSpeedProgress() {
        if (playerAttackSpeedRunnable != null) {
            playerAttackSpeedHandler.removeCallbacks(playerAttackSpeedRunnable);
        }
    }


    private void stopMonsterAttackTimer() {
        if (monsterAttackRunnable != null) {
            monsterAttackHandler.removeCallbacks(monsterAttackRunnable);
        }
    }

    private void stopMonsterAttackSpeedProgress() {
        if (monsterAttackSpeedRunnable != null) {
            monsterAttackSpeedHandler.removeCallbacks(monsterAttackSpeedRunnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPlayerAttackTimer();
        stopMonsterAttackTimer(); // Ensure both timers are stopped
        stopPlayerAttackSpeedProgress();
        stopMonsterAttackSpeedProgress();
    }
}
