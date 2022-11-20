package com.nsu.rpgstats.data.items;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.network.services.ItemService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestItemRepository implements ItemRepository{
    private static final String TAG = RestItemRepository.class.getSimpleName();
    private final ItemService service;

    public RestItemRepository(ItemService service) {
        this.service = service;
    }

    @Override
    public void getItems(int gameSystemId, RepositoryCallback<List<Item>> callback) {
        service.getItems(gameSystemId)
                .enqueue(new Callback<List<Item>>() {
                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            for (Item item: response.body()) {
                                getItemTags(gameSystemId, item.getId(), (result) ->{
                                    if (result instanceof Result.Success) {
                                        item.setTags(((Result.Success<List<Tag>>) result).data);
                                        callback.onComplete(new Result.Success<>(response.body()));
                                    }
                                    if (result instanceof Result.Error) {
                                        callback.onComplete(new Result.Error<>(((Result.Error<List<Tag>>) result).throwable));
                                    }
                                });
                                getItemModifiers(gameSystemId, item.getId(), (result) ->{
                                    if (result instanceof Result.Success) {
                                        item.setModifiers(((Result.Success<List<Modifier>>) result).data);
                                        callback.onComplete(new Result.Success<>(response.body()));
                                    }
                                    if (result instanceof Result.Error) {
                                        callback.onComplete(new Result.Error<>(((Result.Error<List<Modifier>>) result).throwable));
                                    }
                                });
                            }
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void getItem(int gameSystemId, int id, RepositoryCallback<Item> callback) {
        service.getItem(gameSystemId, id)
                .enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        Log.d(TAG, "Response: " + response);
                        getItemTags(gameSystemId, response.body().getId(), (result) ->{
                            if (result instanceof Result.Success) {
                                response.body().setTags(((Result.Success<List<Tag>>) result).data);
                                callback.onComplete(new Result.Success<>(response.body()));
                            }
                            if (result instanceof Result.Error) {
                                callback.onComplete(new Result.Error<>(((Result.Error<List<Tag>>) result).throwable));
                            }
                        });
                        getItemModifiers(gameSystemId, response.body().getId(), (result) ->{
                            if (result instanceof Result.Success) {
                                response.body().setModifiers(((Result.Success<List<Modifier>>) result).data);
                                callback.onComplete(new Result.Success<>(response.body()));
                            }
                            if (result instanceof Result.Error) {
                                callback.onComplete(new Result.Error<>(((Result.Error<List<Modifier>>) result).throwable));
                            }
                        });
                        callback.onComplete(new Result.Success<>(response.body()));
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void addItem(int gameSystemId, Item item, RepositoryCallback<Item> callback) {
        service.addItem(gameSystemId, item)
                .enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        Log.d(TAG, "Response: " + response);
                        callback.onComplete(new Result.Success<>(response.body()));
                        addItemTags(gameSystemId ,response.body().getId(), item.getTags(), (result -> {
                            if (result instanceof Result.Success) {
                                response.body().setTags(((Result.Success<List<Tag>>) result).data);
                            }
                            if (result instanceof Result.Error) {
                                callback.onComplete(new Result.Error<>(((Result.Error<List<Tag>>) result).throwable));
                            }
                        }));
                        addItemModifiers(gameSystemId ,response.body().getId(), item.getModifiers(), (result -> {
                            if (result instanceof Result.Success) {
                                response.body().setModifiers(((Result.Success<List<Modifier>>) result).data);
                            }
                            if (result instanceof Result.Error) {
                                callback.onComplete(new Result.Error<>(((Result.Error<List<Modifier>>) result).throwable));
                            }
                        }));
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void editItem(int gameSystemId, int id, Item item, RepositoryCallback<Item> callback) {
        service.editItem(gameSystemId, id, item)
                .enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        Log.d(TAG, "Response: " + response);
                        getItemTags(gameSystemId, id, tags -> {
                            if (tags instanceof Result.Success) {
                                List<Tag> tagList = ((Result.Success<List<Tag>>) tags).data;

                                List<Tag> addedTags = new ArrayList<>(item.getTags());
                                addedTags.removeAll(tagList);

                                List<Tag> deletedTags = new ArrayList<>(tagList);
                                deletedTags.removeAll(item.getTags());

                                addItemTags(gameSystemId, id, addedTags, (x) -> {}); //
                                for (Tag deleteTag : deletedTags) {
                                    deleteItemTag(gameSystemId, id, deleteTag, (x) -> {}); //
                                }
                            }
                        });
                        getItemModifiers(gameSystemId, id, modifiers-> {
                            if (modifiers instanceof Result.Success) {
                                List<Modifier> modifierList = ((Result.Success<List<Modifier>>) modifiers).data;

                                List<Modifier> addedModifiers = new ArrayList<>(item.getModifiers());
                                addedModifiers.removeAll(modifierList);

                                List<Modifier> deletedModifiers = new ArrayList<>(modifierList);
                                deletedModifiers.removeAll(item.getModifiers());

                                addItemModifiers(gameSystemId, id, addedModifiers, (x) -> {});
                                for (Modifier deletedModifier : deletedModifiers) {
                                    deleteItemModifier(gameSystemId, id, deletedModifier, (x) -> {});
                                }
                            }
                        });
                        callback.onComplete(new Result.Success<>(item));
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void getItemTags(int gameSystemId, int itemId, RepositoryCallback<List<Tag>> callback) {
        service.getItemTags(gameSystemId, itemId)
                .enqueue(new Callback<List<Tag>>() {
                    @Override
                    public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                        Log.d(TAG, "Response: " + response);
                        callback.onComplete(new Result.Success<>(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<Tag>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void addItemTags(int gameSystemId, int itemId, List<Tag> tags, RepositoryCallback<List<Tag>> callback) {
        service.addItemTags(gameSystemId, itemId, tags)
                .enqueue(new Callback<List<Tag>>() {
                    @Override
                    public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                        Log.d(TAG, "Response: " + response);
                        callback.onComplete(new Result.Success<>(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<Tag>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void deleteItemTag(int gameSystemId, int itemId, Tag tag, RepositoryCallback<Tag> callback) {
        service.deleteItemTag(gameSystemId, itemId, tag.getId())
            .enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    Log.d(TAG, "Response: " + response);
                    callback.onComplete(new Result.Success<>(response.body()));
                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {
                    callback.onComplete(new Result.Error<>(t));
                }
            });
    }

    @Override
    public void getItemModifiers(int gameSystemId, int itemId, RepositoryCallback<List<Modifier>> callback) {
        service.getItemModifiers(gameSystemId, itemId)
                .enqueue(new Callback<List<Modifier>>() {
                    @Override
                    public void onResponse(Call<List<Modifier>> call, Response<List<Modifier>> response) {
                        Log.d(TAG, "Response: " + response);
                        callback.onComplete(new Result.Success<>(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<Modifier>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void addItemModifiers(int gameSystemId, int itemId, List<Modifier> modifiers, RepositoryCallback<List<Modifier>> callback) {
        service.addItemModifiers(gameSystemId, itemId, modifiers)
                .enqueue(new Callback<List<Modifier>>() {
                    @Override
                    public void onResponse(Call<List<Modifier>> call, Response<List<Modifier>> response) {
                        Log.d(TAG, "Response: " + response);
                        callback.onComplete(new Result.Success<>(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<Modifier>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void deleteItemModifier(int gameSystemId, int itemId, Modifier modifier, RepositoryCallback<Modifier> callback) {
        service.deleteItemModifier(gameSystemId, itemId, modifier.getId())
                .enqueue(new Callback<Modifier>() {
                    @Override
                    public void onResponse(Call<Modifier> call, Response<Modifier> response) {
                        Log.d(TAG, "Response: " + response);
                        callback.onComplete(new Result.Success<>(response.body()));
                    }
                    @Override
                    public void onFailure(Call<Modifier> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }
}
