package com.nsu.rpgstats.ui.sessions;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.nsu.rpgstats.R;

public class InvitePlayerPopup {

    private final InvitePlayerPopupListener listener;

    public InvitePlayerPopup(InvitePlayerPopupListener listener) {
        this.listener = listener;
    }


    public void show(final View contextView, String link) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) contextView.getContext().getSystemService(contextView.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_invite_players_to_session, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(contextView, Gravity.CENTER, 0, 0);


        EditText linkText = popupView.findViewById(R.id.link_text);
        linkText.setText(link);

        Button copyButton = popupView.findViewById(R.id.copy_button);
        Button backButton = popupView.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> popupWindow.dismiss());
        copyButton.setOnClickListener(v -> {
            listener.onCopyLink(link);
            popupWindow.dismiss();
        });

    }

}
