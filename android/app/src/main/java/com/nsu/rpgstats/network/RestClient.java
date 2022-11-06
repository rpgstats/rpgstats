package com.nsu.rpgstats.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient restClient;
    private final String BASE_URL = "localhost:8080/";
    private final RpgstatsService rpgstatsService;
    private final ItemService itemService;
    private final TagService tagService;
    private final ModifierService modifierService;

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    public RpgstatsService getRpgstatsService() {
        return rpgstatsService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public TagService getTagService() {
        return tagService;
    }

    public ModifierService getModifierService() {
        return modifierService;
    }

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        rpgstatsService = retrofit.create(RpgstatsService.class);
        itemService = retrofit.create(ItemService.class);
        tagService = retrofit.create(TagService.class);
        modifierService = retrofit.create(ModifierService.class);
    }
}
