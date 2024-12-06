package com.example.talev1_0.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.R;
import com.example.talev1_0.monsters.BaseMonster;

import java.util.List;

public class MonsterAdapter extends RecyclerView.Adapter<MonsterAdapter.MonsterViewHolder> {

    private List<BaseMonster> monsters;
    private OnMonsterActionListener dropTableClickListener;
    private OnMonsterActionListener fightClickListener;

    public MonsterAdapter(List<BaseMonster> monsters, OnMonsterActionListener dropTableClickListener, OnMonsterActionListener fightClickListener) {
        this.monsters = monsters;
        this.dropTableClickListener = dropTableClickListener;
        this.fightClickListener = fightClickListener;
    }

    @NonNull
    @Override
    public MonsterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monster, parent, false);
        return new MonsterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonsterViewHolder holder, int position) {
        BaseMonster monster = monsters.get(position);
        holder.monsterNameTextView.setText(monster.getName());

        holder.dropTableButton.setOnClickListener(v -> dropTableClickListener.onAction(monster));
        holder.fightButton.setOnClickListener(v -> fightClickListener.onAction(monster));
    }

    @Override
    public int getItemCount() {
        return monsters.size();
    }

    static class MonsterViewHolder extends RecyclerView.ViewHolder {
        TextView monsterNameTextView;
        Button dropTableButton;
        Button fightButton;

        public MonsterViewHolder(@NonNull View itemView) {
            super(itemView);
            monsterNameTextView = itemView.findViewById(R.id.monster_name_textview);
            dropTableButton = itemView.findViewById(R.id.drop_table_button);
            fightButton = itemView.findViewById(R.id.fight_button);
        }
    }

    public interface OnMonsterActionListener {
        void onAction(BaseMonster monster);
    }
}
