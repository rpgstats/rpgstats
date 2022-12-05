package com.nsu.rpgstats;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import com.nsu.rpgstats.data.constraints.ConstraintRepository;
import com.nsu.rpgstats.data.gamesystems.GameSystemsRepository;
import com.nsu.rpgstats.data.gamesystems.PlugGameSystemsRepository;
import com.nsu.rpgstats.data.items.ItemRepository;
import com.nsu.rpgstats.data.modifiers.ModifierRepository;
import com.nsu.rpgstats.data.parameters.ParameterRepository;
import com.nsu.rpgstats.data.constraints.PlugConstraintRepository;
import com.nsu.rpgstats.data.modifiers.PlugModifierRepository;
import com.nsu.rpgstats.data.parameters.PlugParameterRepository;
import com.nsu.rpgstats.data.properties.PlugPropertyRepository;
import com.nsu.rpgstats.data.properties.PropertyRepository;
import com.nsu.rpgstats.data.gamesystems.RestGameSystemsRepository;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.data.items.PlugItemRepository;
import com.nsu.rpgstats.data.tags.PlugTagRepository;
import com.nsu.rpgstats.data.tags.TagRepository;
import com.nsu.rpgstats.network.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppContainer {
    public ActivityResultLauncher<Intent> fileActivityLauncher;
    public ActivityResultLauncher<Intent> iconActivityLauncher;
    public ActivityResultLauncher<Intent> backgroundActivityLauncher;

    public GameSystem currentGameSystem = null;
    public ItemRepository itemRepository = new PlugItemRepository();
    public TagRepository tagRepository = new PlugTagRepository();
    public ModifierRepository modifierRepository = new PlugModifierRepository();
    public ParameterRepository parameterRepository = new PlugParameterRepository();
    public PropertyRepository propertyRepository = new PlugPropertyRepository();
    public ConstraintRepository constraintRepository = new PlugConstraintRepository();

    private final Context context;
    public GameSystemsRepository gameSystemsRepository;

    private static final String TAG = AppContainer.class.getSimpleName();

    public AppContainer(Context context) {
        this.context = context;
        Log.e(TAG, context.toString());
        RestClient restClient = RestClient.getInstance(getServerAddrFromConfig());
        //gameSystemsRepository = new RestGameSystemsRepository(restClient.getRpgstatsService());
        gameSystemsRepository = new PlugGameSystemsRepository(); // TODO ubrat' i decommit
    }

    private String getServerAddrFromConfig() {
        Properties props = new Properties();
        InputStream rawResource = context.getResources().openRawResource(R.raw.config);
        try {
            props.load(rawResource);
            Log.i(TAG, "Successfully set addr to " + props.getProperty("server_address"));
            return props.getProperty("server_address");
        } catch (IOException e) {
            Log.e(TAG, "Can not get address from config file");
            return "";
        }
    }

}
