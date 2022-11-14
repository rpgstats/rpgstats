package com.nsu.rpgstats;

import static android.util.Log.i;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Base class for maintaining global application state.
public class RpgstatsApplication extends Application {
    // Instance of AppContainer that will be used by all the Activities of the app
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        this.appContainer = new AppContainer(getApplicationContext());
    }
}
