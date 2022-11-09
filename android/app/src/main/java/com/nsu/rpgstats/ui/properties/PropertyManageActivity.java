package com.nsu.rpgstats.ui.properties;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.ConstraintRepository;
import com.nsu.rpgstats.data.ModifierRepository;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.databinding.ActivityPropertyManageBinding;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertyManageActivity extends AppCompatActivity {
    public static Integer MODE_ADD = 1;
    public static Integer MODE_EDIT = 2;
    private ActivityPropertyManageBinding binding;
    // Property that will be sent to server if submitted
    private PropertyRepository repo;
    private Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyManageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_property_manage);

        setAllContent();
    }

    private void setAllContent() {
        repo = ((RpgstatsApplication) getApplication()).appContainer.propertyRepository;
        Bundle b = getIntent().getExtras();
        Integer mode = b.getInt("Mode");

        Button confirmButton = findViewById(R.id.confirmAddPropertyButton);
        EditText nameInput = findViewById(R.id.newPropName);

        Property p = repo.getProperty(0, b.getInt("id"));
        property = new Property(p);

        setModifiersList();
        setAddModifiersButton();
        setConstraintsList();
        setAddConstraintsButton();

        if (MODE_EDIT.equals(mode)) {
            TextView head = findViewById(R.id.propHead);
            head.setText(R.string.edit_property);
            nameInput.setText(property.getName());
        }

        confirmButton.setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            //Property newProp = new Property(name, false);
            if (mode.equals(PropertyManageActivity.MODE_ADD)) {
                // TODO: send Property as a new one
            }
            if (mode.equals(PropertyManageActivity.MODE_EDIT)) {
                // TODO: send Property as the changed one
            }
        });
    }

    public void setModifiersList() {
        ListView modifs = findViewById(R.id.property_manage_modifiers_list);
        modifs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, property.getModifiers()));
        modifs.setOnItemClickListener((adapterView, view, i, l) -> {
            property.getModifiers().remove(i);
            ((ListView)findViewById(R.id.property_manage_modifiers_list)).invalidateViews();
        });
    }

    public void setAddModifiersButton() {
        Button addModif = findViewById(R.id.add_modifier_button);
        // On button click a dialog shows up that contains all modifiers that are not currently in property
        addModif.setOnClickListener(view -> {
            ModifierRepository rep = ((RpgstatsApplication) getApplication()).appContainer.modifierRepository;
            // All modifiers
            List<Modifier> modifs = rep.getModifiers(0);

            // All modifiers that are not currently in property
            List<Modifier> absentModifs = new ArrayList<>();

            // All modifiers that will be added to property after confirmation
            List<Modifier> newModifs = new ArrayList<>();

            for (Modifier m : modifs) {
                if (!property.getModifiers().contains(m)) {
                    absentModifs.add(m);
                }
            }
            int length = absentModifs.size();
            String[] modifNames = new String[length];

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose modifiers to add");

            boolean[] checked = new boolean[length];
            for (int i = 0; i < length; ++i) {
                modifNames[i] = absentModifs.get(i).getName();
                checked[i] = false;
            }
            builder.setMultiChoiceItems(modifNames, checked, (DialogInterface.OnMultiChoiceClickListener)
                    (dialogInterface, i, b) -> {
                Modifier clickedModif = absentModifs.get(i);
                if (b) {
                    newModifs.add(clickedModif);
                } else {
                    newModifs.remove(clickedModif);
                }
            });
            builder.setPositiveButton("Add", (dialogInterface, i) -> {
                property.getModifiers().addAll(newModifs);
                // Refresh the list after adding new modifiers (maybe there is a better way)
                ((ListView)findViewById(R.id.property_manage_modifiers_list)).invalidateViews();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void setConstraintsList() {
        ListView constrs = findViewById(R.id.property_manage_constraints_list);
        constrs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, property.getConstraints()));
        constrs.setOnItemClickListener((adapterView, view, i, l) -> {
            property.getConstraints().remove(i);
            ((ListView)findViewById(R.id.property_manage_constraints_list)).invalidateViews();
        });
    }

    public void setAddConstraintsButton() {
        Button addConstr = findViewById(R.id.add_constraint_button);
        // On click the dialog will show up that will contain all constraints that are not in property now
        addConstr.setOnClickListener(view -> {
            ConstraintRepository rep = ((RpgstatsApplication) getApplication()).appContainer.constraintRepository;
            // All constraints
            List<Constraint> constrs = rep.getConstraints(0);

            // All constraints that are not currently in property
            List<Constraint> absentConstrs = new ArrayList<>();

            // All constraints that will be added to property after confirmation
            List<Constraint> newConstrs = new ArrayList<>();

            for (Constraint c : constrs) {
                if (!property.getConstraints().contains(c)) {
                    absentConstrs.add(c);
                }
            }
            int length = absentConstrs.size();
            String[] constrNames = new String[length];

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose constraints to add");

            boolean[] checked = new boolean[length];
            for (int i = 0; i < length; ++i) {
                constrNames[i] = absentConstrs.get(i).getName();
                checked[i] = false;
            }
            builder.setMultiChoiceItems(constrNames, checked, (DialogInterface.OnMultiChoiceClickListener)
                    (dialogInterface, i, b) -> {
                        Constraint clickedModif = absentConstrs.get(i);
                        if (b) {
                            newConstrs.add(clickedModif);
                        } else {
                            newConstrs.remove(clickedModif);
                        }
                    });
            builder.setPositiveButton("Add", (dialogInterface, i) -> {
                property.getConstraints().addAll(newConstrs);
                // Refresh the list after adding new modifiers (maybe there is a better way)
                ((ListView)findViewById(R.id.property_manage_constraints_list)).invalidateViews();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}