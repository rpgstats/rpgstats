package com.nsu.rpgstats.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient restClient;
    private final String BASE_URL = "localhost:8080/";
    private final RpgstatsService rpgstatsService;

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    public RpgstatsService getRpgstatsService() {
        return rpgstatsService;
    }

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        rpgstatsService = retrofit.create(RpgstatsService.class);
    }
}
