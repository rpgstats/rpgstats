package com.nsu.rpgstats.ui.profile;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.nsu.rpgstats.R;

public class ProfileSettingsPopup {

    private ChangeModeListener changeModeListener;

    public ProfileSettingsPopup(ChangeModeListener changeModeListener) {
        this.changeModeListener = changeModeListener;
    }

    public void show(final View view) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_profile_settings, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupView.findViewById(R.id.back_button).setOnClickListener((v) ->
        {
            popupWindow.dismiss();
        });
        popupView.findViewById(R.id.change_mode_button).setOnClickListener(v-> changeModeListener.onChangeMode());

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener((v, event) -> {
            v.performClick();
            return true;
        });

    }

    private void setup() {

    }

}
