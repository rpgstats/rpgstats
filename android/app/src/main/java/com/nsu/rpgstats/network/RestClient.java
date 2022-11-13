package com.nsu.rpgstats.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient restClient;

    // Если тестируется на телефоне и он подключен к той же сети, что и комп, то
    // нужно здесь указать адрес компа в локалке (его можно найти в ipconfig)
    // подробнее -- https://stackoverflow.com/questions/4779963/how-can-i-access-my-localhost-from-my-android-device
    private final String SERVER_ADDRESS = "localhost";

    private final String BASE_URL = "http://" + SERVER_ADDRESS + ":8080/";
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
