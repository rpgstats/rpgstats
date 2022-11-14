package com.nsu.rpgstats.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.network.DTO.ModifierDTO;
import com.nsu.rpgstats.network.ModifierService;
import com.nsu.rpgstats.network.RestClient;
import com.nsu.rpgstats.network.RpgstatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestModifierRepository implements ModifierRepository{
    private static final String TAG = RestModifierRepository.class.getSimpleName();
    private final ModifierService service;

    public RestModifierRepository() {
        service = RestClient.getInstance().getModifierService();
    }

    @Override
    public List<Modifier> getModifiers(int gameSystemId) {
        final MutableLiveData<List<Modifier>> modifiers = new MutableLiveData<>();
        service.getModifiers(gameSystemId)
                .enqueue(new Callback<List<ModifierDTO>>() {
                    @Override
                    public void onResponse(Call<List<ModifierDTO>> call, Response<List<ModifierDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Modifier> modifierList = new ArrayList<>();
                            for (ModifierDTO modifier :
                                    response.body()) {
                                modifierList.add(new Modifier(modifier, new PlugParameterRepository())); // todo rest parameter repository
                            }
                            modifiers.setValue(modifierList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ModifierDTO>> call, Throwable t) {
                        modifiers.setValue(null);
                    }
                });
        return modifiers.getValue();
    }

    @Override
    public Modifier getModifier(int gameSystemId, int id) {
        final MutableLiveData<Modifier> modifiers = new MutableLiveData<>();
        service.getModifier(gameSystemId, id)
                .enqueue(new Callback<ModifierDTO>() {
                    @Override
                    public void onResponse(Call<ModifierDTO> call, Response<ModifierDTO> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            modifiers.setValue(new Modifier(response.body(), new PlugParameterRepository())); // todo rest parameter repository
                        }
                    }

                    @Override
                    public void onFailure(Call<ModifierDTO> call, Throwable t) {
                        modifiers.setValue(null);
                    }
                });
        return modifiers.getValue();
    }

    @Override
    public int addModifier(int gameSystemId, Modifier modifier) {
        final MutableLiveData<Modifier> modifiers = new MutableLiveData<>();
        service.addModifier(gameSystemId, new ModifierDTO(modifier, gameSystemId))
                .enqueue(new Callback<ModifierDTO>() {
                    @Override
                    public void onResponse(Call<ModifierDTO> call, Response<ModifierDTO> response) {
                        if (response.body() != null) {
                            modifiers.setValue(new Modifier(response.body(), new PlugParameterRepository())); // todo rest parameter repository
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<ModifierDTO> call, Throwable t) {
                        modifiers.setValue(null);
                    }
                });
        return modifiers.getValue().getId();
    }

    @Override
    public int editModifier(int gameSystemId, int id, Modifier modifier) {
        final MutableLiveData<Modifier> modifiers = new MutableLiveData<>();
        service.editModifier(gameSystemId, id, new ModifierDTO(modifier, gameSystemId))
                .enqueue(new Callback<ModifierDTO>() {
                    @Override
                    public void onResponse(Call<ModifierDTO> call, Response<ModifierDTO> response) {
                        if (response.body() != null) {
                            modifiers.setValue(new Modifier(response.body(), new PlugParameterRepository())); // todo rest parameter repository
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<ModifierDTO> call, Throwable t) {
                        modifiers.setValue(null);
                    }});
        return modifiers.getValue() == null ? -1 : modifiers.getValue().getId();
    }
}
