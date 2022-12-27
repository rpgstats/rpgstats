package com.nsu.rpgstats.network;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.loader.ResourcesProvider;
import android.util.Log;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.network.dto.LoginRequest;
import com.nsu.rpgstats.network.dto.LoginResponse;
import com.nsu.rpgstats.network.dto.MessageResponse;
import com.nsu.rpgstats.network.dto.SignupRequest;
import com.nsu.rpgstats.network.services.AuthService;
import com.nsu.rpgstats.network.services.GamesystemsService;
import com.nsu.rpgstats.network.services.SessionService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final String TAG = RestClient.class.getSimpleName();

    private static RestClient restClient;

    // Если тестируется на телефоне и он подключен к той же сети, что и комп, то
    // нужно здесь указать адрес компа в локалке (его можно найти в ipconfig)
    // подробнее -- https://stackoverflow.com/questions/4779963/how-can-i-access-my-localhost-from-my-android-device

    private final GamesystemsService gamesystemsService;
    private final AuthService authService;
    private final SessionService sessionService;
    private final AuthInterceptor authInterceptor;

    public static RestClient getInstance(String serverAddress) {
        if (restClient == null) {
            restClient = new RestClient(serverAddress);
        }

        return restClient;
    }

    public GamesystemsService getRpgstatsService() {
        return gamesystemsService;
    }

    private RestClient(String serverAddress) {
        authInterceptor = new AuthInterceptor();
        String BASE_URL = "http://" + serverAddress + ":8080/api/v1/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        gamesystemsService = retrofit.create(GamesystemsService.class);
        authService = retrofit.create(AuthService.class);
        sessionService = retrofit.create(SessionService.class);
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setToken(String token) {
        authInterceptor.setToken(token);
    }
}
