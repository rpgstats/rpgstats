package com.nsu.rpgstats.ui.sessions;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nsu.rpgstats.R;

public class DeleteSessionPopup  {

    private DeleteSessionPopupListener listener;

    public DeleteSessionPopup(DeleteSessionPopupListener listener) {
        this.listener = listener;
    }

    public void show(final View contextView) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) contextView.getContext().getSystemService(contextView.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_warning_with_two_options, null);
        ((TextView)popupView.findViewById(R.id.warningText)).setText("Are you sure you want to delete this session?");

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(contextView, Gravity.CENTER, 0, 0);


        Button backButton = popupView.findViewById(R.id.yes_button);
        Button addButton = popupView.findViewById(R.id.no_button);
        backButton.setOnClickListener(v -> listener.onDeleteSession());
        addButton.setOnClickListener(v -> popupWindow.dismiss());
    }
}
