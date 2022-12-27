package com.nsu.rpgstats.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.rpgstats.ui.gamesystems.GameSystemsActivity;
import com.nsu.rpgstats.ui.sessions.SessionsActivity;

public class AuthActivity extends Activity implements AuthListener {
    private ViewBinding activityBinding;

    @Override
    public void onSuccessAuth() {
        Intent i = new Intent(this, SessionsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMessage(String message) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(
                Activity.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        View rootView = getWindow().getDecorView().getRootView();
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
