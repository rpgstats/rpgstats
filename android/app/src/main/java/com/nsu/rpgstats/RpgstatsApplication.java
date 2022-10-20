package com.nsu.rpgstats;

import android.app.Application;

// Base class for maintaining global application state.
public class RpgstatsApplication extends Application {
    // Instance of AppContainer that will be used by all the Activities of the app
    public AppContainer appContainer = new AppContainer();
}
