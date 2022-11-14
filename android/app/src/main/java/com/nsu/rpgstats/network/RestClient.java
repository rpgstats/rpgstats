package com.nsu.rpgstats.network;

import android.util.Log;

import com.nsu.rpgstats.network.dto.LoginRequest;
import com.nsu.rpgstats.network.dto.LoginResponse;
import com.nsu.rpgstats.network.dto.MessageResponse;
import com.nsu.rpgstats.network.dto.SignupRequest;
import com.nsu.rpgstats.network.services.AuthService;
import com.nsu.rpgstats.network.services.GamesystemsService;

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
    private final String SERVER_ADDRESS = "192.168.27.29";

    private final String BASE_URL = "http://" + SERVER_ADDRESS + ":8080/api/v1/";
    private final GamesystemsService gamesystemsService;
    private final AuthService authService;
    private final AuthInterceptor authInterceptor;

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    public GamesystemsService getRpgstatsService() {
        return gamesystemsService;
    }

    private RestClient() {
        authInterceptor = new AuthInterceptor();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        gamesystemsService = retrofit.create(GamesystemsService.class);
        authService = retrofit.create(AuthService.class);

        // todo: This setup exsists only while not realized login functionality
        testSetupAuth();

    }

    private void testSetupAuth() {
        authService.signup(
                        new SignupRequest("test", "test", "test"))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.code() != 200) {
                            Log.e(TAG, "Can not signup!!!:" + response.code() + " " + call.request().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.e(TAG, "Can not signup!!!", t);
                    }
                });


        authService.login(
                        new LoginRequest("test", "test"))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() != 200) {
                            Log.e(TAG, "Can not login!!!:" + response.code() + " " + response.body());
                        } else {
                            assert response.body() != null;
                            Log.e(TAG, "Set token to " + response.body().getAuthToken());
                            authInterceptor.setToken(response.body().getAuthToken());
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();
    }

}
