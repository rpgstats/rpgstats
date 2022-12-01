package com.nsu.rpgstats.ui.character;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.ItemSlot;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CharacterEquipmentActivity extends AppCompatActivity {
    private List<ItemSlot> slots;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_equipment);

        plugSlots();
        initGrid();
    }

    private void initGrid() {
        GridView slotsGrid = findViewById(R.id.charEquipGrid);
        SlotsAdapter adapter = new SlotsAdapter(this, slots, items);
        slotsGrid.setNumColumns(3);

        slotsGrid.setAdapter(adapter);
    }

    private void plugSlots() {
        slots = new ArrayList<>();
        items = new ArrayList<>();

        // Tags
        ArrayList<Tag> helmetTags = new ArrayList<>();
        helmetTags.add(new Tag(0, "Helmet", "now", false));
        ArrayList<Tag> armorTags = new ArrayList<>();
        armorTags.add(new Tag(1, "Armor", "now", false));
        ArrayList<Tag> ringTags = new ArrayList<>();
        ringTags.add(new Tag(2, "Ring", "now", false));
        ArrayList<Tag> bootsTags = new ArrayList<>();
        bootsTags.add(new Tag(3, "Boots", "now", false));
        ArrayList<Tag> necklaceTags = new ArrayList<>();
        necklaceTags.add(new Tag(4, "Necklace", "now", false));
        ArrayList<Tag> bracersTags = new ArrayList<>();
        bracersTags.add(new Tag(5, "Bracers", "now", false));

        // Types of constraints
        ArrayList<Constraint> helmetConstraints = new ArrayList<>();
        helmetConstraints.add(new Constraint(0, "Only helmets", false, helmetTags));
        ArrayList<Constraint> armorConstraints = new ArrayList<>();
        armorConstraints.add(new Constraint(1, "Only armor", false, armorTags));
        ArrayList<Constraint> ringConstraints = new ArrayList<>();
        ringConstraints.add(new Constraint(2, "Only ring", false, ringTags));
        ArrayList<Constraint> bootsConstraints = new ArrayList<>();
        bootsConstraints.add(new Constraint(3, "Only boots", false, bootsTags));
        ArrayList<Constraint> necklaceConstraints = new ArrayList<>();
        necklaceConstraints.add(new Constraint(4, "Only necklace", false, necklaceTags));
        ArrayList<Constraint> bracersConstraints = new ArrayList<>();
        bracersConstraints.add(new Constraint(5, "Only bracers", false, bracersTags));

        ArrayList<Modifier> helmetModifiers = new ArrayList<>();
        helmetModifiers.add(new Modifier(0, "Hp up", 5, new Parameter(0, "Hp", new Date(), 0, 999)));
        items.add(new Item(0, 1337, "Helmet for hp up", helmetTags, helmetModifiers, false));

        ArrayList<Modifier> armorModifiers = new ArrayList<>();
        armorModifiers.add(new Modifier(1, "Defense up", 5, new Parameter(1, "Defense", new Date(), 0, 999)));
        items.add(new Item(1, 1337, "Armor for defense up", armorTags, armorModifiers, false));

        slots.add(new ItemSlot(null, helmetConstraints, true));
        slots.add(new ItemSlot(null, armorConstraints, true));
        slots.add(new ItemSlot(null, ringConstraints, true));
        slots.add(new ItemSlot(null, ringConstraints, true));
        slots.add(new ItemSlot(null, ringConstraints, true));
        slots.add(new ItemSlot(null, ringConstraints, true));
        slots.add(new ItemSlot(null, bootsConstraints, true));
        slots.add(new ItemSlot(null, necklaceConstraints, true));
        slots.add(new ItemSlot(null, bracersConstraints, true));
    }
}