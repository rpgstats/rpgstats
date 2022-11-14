package com.nsu.rpgstats.ui.gamesystems;

public class CreationGameResult {
    private final String errorText;

    public CreationGameResult(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}
