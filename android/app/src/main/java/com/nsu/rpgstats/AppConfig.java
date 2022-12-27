package com.nsu.rpgstats;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final String TAG = AppConfig.class.getSimpleName();

    private String serverAddress;
    private boolean loggingHttpEnabled;

    private final Context context;

    public AppConfig(Context context) {
        this.context = context;
    }

    public void LoadConfig() {
        Properties props = new Properties();
        InputStream rawResource = context.getResources().openRawResource(R.raw.config);
        try {
            props.load(rawResource);
        } catch (IOException e) {
            Log.e(TAG, "Can not get config file");
        }
        String servAddr = props.getProperty("server_address");
        String logging = props.getProperty("http_logging");
        serverAddress = servAddr;
        if (logging != null) {
            loggingHttpEnabled = "true".equals(logging);
            Log.d(TAG, "Set logging http to " + loggingHttpEnabled);
        }
    }

    public boolean isLoggingHttpEnabled() {
        return loggingHttpEnabled;
    }

    public String getServerAddress() {
        return serverAddress;
    }
}
