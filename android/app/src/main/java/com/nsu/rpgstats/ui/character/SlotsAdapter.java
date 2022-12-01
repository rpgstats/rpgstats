package com.nsu.rpgstats.ui.character;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.ItemSlot;

import java.util.List;

public class SlotsAdapter extends BaseAdapter {
    private final Context context;
    private final List<ItemSlot> slots;
    private final List<Item> items;

    public SlotsAdapter(Context context, List<ItemSlot> slots, List<Item> items) {
        this.context = context;
        this.slots = slots;
        this.items = items;
    }

    @Override
    public int getCount() {
        return slots.size();
    }

    @Override
    public Object getItem(int i) {
        return slots.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView slot = new ImageView(context);
        slot.setBackgroundResource(R.drawable.rounded_border);
        slot.setFocusable(true);
        slot.setClickable(true);
        slot.setMinimumWidth(50);
        slot.setMinimumHeight(50);
        slot.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            StringBuilder title = new StringBuilder();
            for (Constraint c : slots.get(i).getConstraintList()) {
                title.append(c.getName()).append("\n");
            }
            builder.setTitle(title.toString());

            String[] itemNames = new String[items.size()];
            for (int j = 0; j < items.size(); ++j) {
                itemNames[j] = items.get(j).getName();
            }
            final Integer[] checked = new Integer[1];

            builder.setSingleChoiceItems(itemNames, -1, (dialogInterface, i1) -> {
                checked[0] = i1;
            });
            // TODO: callback: send changed item in slot
            builder.setPositiveButton("Equip", (dialogInterface, i12) -> {
                Item checkedItem = items.get(checked[0]);
                String type = checkedItem.getTags().get(0).getName();
                slots.get(i).setItem(checkedItem);
                switch (type) {
                    case "Helmet":
                        slot.setImageResource(R.drawable.ic_helmet_24);
                        break;
                    case "Armor":
                        slot.setImageResource(R.drawable.ic_armor_24);
                        break;
                    default:
                        slot.setImageResource(R.drawable.plus_sign);
                        break;
                }
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i13) -> {
                Toast.makeText(context, "Krasivoe", Toast.LENGTH_SHORT).show();
            });
            builder.create().show();
        });
        return slot;
    }
}
