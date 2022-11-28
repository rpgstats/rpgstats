package com.nsu.rpgstats.ui.sessions;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.AddCharacterToSessionPopupBinding;

public class AddCharacterToSessionPopup {
    public void show(final View view) {
        view.getRootView().getBackground().setAlpha(128);
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_character_to_session_popup, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        Button backButton = popupView.findViewById(R.id.add_button);
        Button addButton = popupView.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> popupWindow.dismiss());
        addButton.setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.setOnDismissListener(()->{
            view.getRootView().getBackground().setAlpha(255);
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener((v, event) -> {
            v.performClick();
            return true;
        });

    }
}
