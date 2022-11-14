package com.nsu.rpgstats.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.network.DTO.ItemDTO;
import com.nsu.rpgstats.network.DTO.ModifierDTO;
import com.nsu.rpgstats.network.DTO.TagDTO;
import com.nsu.rpgstats.network.ItemService;
import com.nsu.rpgstats.network.RestClient;
import com.nsu.rpgstats.network.RpgstatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestItemRepository implements ItemRepository{
    private static final String TAG = RestItemRepository.class.getSimpleName();
    private final ItemService service;

    public RestItemRepository() {
        service = RestClient.getInstance().getItemService();
    }

    @Override
    public List<Item> getItems(int gameSystemId) {
        final MutableLiveData<List<Item>> items = new MutableLiveData<>();
        service.getItems(gameSystemId)
                .enqueue(new Callback<List<ItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Item> itemList = new ArrayList<>();
                            for (ItemDTO item : response.body()) {
                                itemList.add(new Item(item));
                            }
                            items.setValue(itemList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ItemDTO>> call, Throwable t) {
                        items.setValue(null);
                    }
                });
        return items.getValue();
    }

    @Override
    public Item getItem(int gameSystemId, int id) {
        final MutableLiveData<Item> items = new MutableLiveData<>();
        service.getItem(gameSystemId, id)
                .enqueue(new Callback<ItemDTO>() {
                    @Override
                    public void onResponse(Call<ItemDTO> call, Response<ItemDTO> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            items.setValue(new Item(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemDTO> call, Throwable t) {
                        items.setValue(null);
                    }
                });
        return items.getValue();
    }

    @Override
    public int addItem(int gameSystemId, Item item) {
        final MutableLiveData<Item> items = new MutableLiveData<>();
        service.addItem(gameSystemId, new ItemDTO(item, gameSystemId))
                .enqueue(new Callback<ItemDTO>() {
                    @Override
                    public void onResponse(Call<ItemDTO> call, Response<ItemDTO> response) {
                        if (response.body() != null) {
                            items.setValue(new Item(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<ItemDTO> call, Throwable t) {
                        items.setValue(null);
                    }
                });
        return items.getValue().getId();
    }

    @Override
    public int editItem(int gameSystemId, int id, Item item) {
        final MutableLiveData<Item> items = new MutableLiveData<>();
        service.editItem(gameSystemId, id, new ItemDTO(item, gameSystemId))
                .enqueue(new Callback<ItemDTO>() {
                    @Override
                    public void onResponse(Call<ItemDTO> call, Response<ItemDTO> response) {
                        if (response.body() != null) {
                            items.setValue(new Item(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<ItemDTO> call, Throwable t) {
                        items.setValue(null);
                    }});
        return items.getValue() == null ? -1 : items.getValue().getId();
    }

    @Override
    public List<Tag> getItemTags(int gameSystemId, int itemId) {
        final MutableLiveData<List<Tag>> tags = new MutableLiveData<>();
        service.getItemTags(gameSystemId, itemId)
                .enqueue(new Callback<List<TagDTO>>() {
                    @Override
                    public void onResponse(Call<List<TagDTO>> call, Response<List<TagDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Tag> tagList = new ArrayList<>();
                            for (TagDTO tag : response.body()) {
                                tagList.add(new Tag(tag));
                            }
                            tags.setValue(tagList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TagDTO>> call, Throwable t) {
                        tags.setValue(null);
                    }
                });
        return tags.getValue();
    }

    @Override
    public List<Tag> addItemTags(int gameSystemId, int itemId, List<Tag> addTags) {
        final MutableLiveData<List<Tag>> tags = new MutableLiveData<>();
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : addTags) {
            tagDTOList.add(new TagDTO(tag, gameSystemId));
        }
        service.addItemTags(gameSystemId, itemId, tagDTOList)
                .enqueue(new Callback<List<TagDTO>>() {
                    @Override
                    public void onResponse(Call<List<TagDTO>> call, Response<List<TagDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Tag> tagList = new ArrayList<>();
                            for (TagDTO tag : response.body()) {
                                tagList.add(new Tag(tag));
                            }
                            tags.setValue(tagList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TagDTO>> call, Throwable t) {
                        tags.setValue(null);
                    }
                });
        return tags.getValue();
    }

    @Override
    public Tag deleteItemTag(int gameSystemId, int itemId, Tag tag) {
        final MutableLiveData<Tag> tags = new MutableLiveData<>();
        service.deleteItemTag(gameSystemId, itemId, tag.getId())
                .enqueue(new Callback<TagDTO>() {
                    @Override
                    public void onResponse(Call<TagDTO> call, Response<TagDTO> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            tags.setValue(new Tag(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<TagDTO> call, Throwable t) {
                        tags.setValue(null);
                    }
                });
        return tags.getValue();
    }

    @Override
    public List<Modifier> getItemModifiers(int gameSystemId, int itemId) {
        final MutableLiveData<List<Modifier>> modifiers = new MutableLiveData<>();
        service.getItemModifiers(gameSystemId, itemId)
                .enqueue(new Callback<List<ModifierDTO>>() {
                    @Override
                    public void onResponse(Call<List<ModifierDTO>> call, Response<List<ModifierDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Modifier> modifierList = new ArrayList<>();
                            for (ModifierDTO modifier :
                                    response.body()) {
                                modifierList.add(new Modifier(modifier , new PlugParameterRepository()));// todo rest parameter repository
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
    public List<Modifier> addItemModifiers(int gameSystemId, int itemId, List<Modifier> addModifiers) {
        final MutableLiveData<List<Modifier>> modifiers = new MutableLiveData<>();
        List<ModifierDTO> modifierDTOList = new ArrayList<>();
        for (Modifier modifier : addModifiers) {
            modifierDTOList.add(new ModifierDTO(modifier, gameSystemId));
        }
        service.addItemModifiers(gameSystemId, itemId, modifierDTOList)
                .enqueue(new Callback<List<ModifierDTO>>() {
                    @Override
                    public void onResponse(Call<List<ModifierDTO>> call, Response<List<ModifierDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Modifier> modifierList = new ArrayList<>();
                            for (ModifierDTO modifier : response.body()) {
                                modifierList.add(new Modifier(modifier, new PlugParameterRepository()));// todo rest parameter repository
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
    public Modifier deleteItemModifier(int gameSystemId, int itemId, Modifier modifier) {
        final MutableLiveData<Modifier> modifiers = new MutableLiveData<>();
        service.deleteItemModifier(gameSystemId, itemId, modifier.getId())
                .enqueue(new Callback<ModifierDTO>() {
                    @Override
                    public void onResponse(Call<ModifierDTO> call, Response<ModifierDTO> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            modifiers.setValue(new Modifier(response.body(), new PlugParameterRepository()));// todo rest parameter repository
                        }
                    }
                    @Override
                    public void onFailure(Call<ModifierDTO> call, Throwable t) {
                        modifiers.setValue(null);
                    }
                });
        return modifiers.getValue();
    }
}
