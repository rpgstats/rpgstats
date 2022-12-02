package com.nsu.rpgstats.ui.character;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.ItemSlot;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.List;

public class SlotsAdapter extends BaseAdapter {
    private final Context context;
    private final List<ItemSlot> slots;
    private final List<Item> items;
    private AlertDialog pickItemDialog;

    public SlotsAdapter(Context context, List<ItemSlot> slots, List<Item> items) {
        this.context = context;
        this.slots = slots;
        this.items = items;
        pickItemDialog = null;
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
        // Custom view to make element of grid square
        SlotView resultView = new SlotView(context);
        ImageView slot = new ImageView(context);
        slot.setBackgroundResource(R.drawable.slot_bg);
        slot.setFocusable(true);
        slot.setClickable(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(10, 10, 10, 10);
        slot.setLayoutParams(params);

        slot.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            StringBuilder title = new StringBuilder();
            for (Constraint c : slots.get(i).getConstraintList()) {
                title.append(c.getName()).append("\n");
            }
            builder.setTitle(title.toString());

            final Integer[] checked = new Integer[1];

            // Filter all items matching clicked slot's constraints
            List<Item> fittingItems = new ArrayList<>();
            for (Item it : items) {
                if (fits(it, slots.get(i).getConstraintList())) {
                    fittingItems.add(it);
                }
            }

            // Names of all fitting items to show
            List<String> itemNamesList = new ArrayList<>();
            if (slots.get(i).getItem() != null) {
                itemNamesList.add("Remove item");
            }
            for (int j = 0; j < fittingItems.size(); ++j) {
                itemNamesList.add(fittingItems.get(j).getName());
            }

            String[] itemNames = itemNamesList.toArray(new String[0]);

            // Make this dialog display a list of items that can be placed in slot
            builder.setSingleChoiceItems(itemNames, -1, (dialogInterface, i1) -> {
                // If there is an item in a slot, then all indexes are moved by the first line 'remove item'
                checked[0] = i1 - (slots.get(i).getItem() == null ? 0 : 1);
                if (pickItemDialog != null) {
                    pickItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            });

            // TODO: callback: send changed item in slot
            builder.setPositiveButton("Equip", (dialogInterface, i12) -> {
                // TODO callback: item removed from slot
                if (checked[0] == -1) {
                    Item item = slots.get(i).getItem();
                    slots.get(i).setItem(null);
                    items.add(item);
                    slot.setImageDrawable(null);
                    return;
                }

                // TODO callback: item placed in slot
                Item checkedItem = fittingItems.get(checked[0]);
                String type = checkedItem.getTags().get(0).getName();
                // If there is an item, then return it to item list
                if (slots.get(i).getItem() != null) {
                    items.add(slots.get(i).getItem());
                }
                slots.get(i).setItem(checkedItem);
                switch (type) {
                    case "Helmet":
                        slot.setImageResource(R.drawable.helmet_icon);
                        break;
                    case "Armor":
                        slot.setImageResource(R.drawable.armor_icon);
                        break;
                    case "Ring":
                        slot.setImageResource(R.drawable.ring_icon);
                        break;
                    default:
                        slot.setImageResource(R.drawable.plus_sign);
                        break;
                }
                items.remove(checkedItem);
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i13) -> {
                Toast.makeText(context, "Krasivoe", Toast.LENGTH_SHORT).show();
            });
            pickItemDialog = builder.create();
            pickItemDialog.setOnShowListener(dialogInterface -> {
                pickItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            });
            pickItemDialog.show();
        });
        resultView.addView(slot);
        return resultView;
    }

    // Checks whether an item fits specified constraints or not
    private boolean fits(Item item, List<Constraint> constraints) {
        boolean result = false;
        for (Constraint c : constraints) {
            List<Tag> itemTags = item.getTags();
            for (Tag it : itemTags) {
                for (Tag ct : c.getTags()) {
                    if (it.equals(ct)) {
                        return !c.isBlackList();
                    }
                }
            }
        }
        return result;
    }
}
