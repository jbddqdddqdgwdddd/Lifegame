package com.victor.renderer;

import java.awt.Color;

import com.victor.model.system.Creature;

public abstract class ColorModel {
    public static enum BackgroundTheme {
        black,
        white;
    }

    BackgroundTheme backgroundTheme = BackgroundTheme.black;

    public BackgroundTheme getBackgroundTheme() {
        return backgroundTheme;
    }

    public void setBackgroundTheme(BackgroundTheme t) {
        this.backgroundTheme = t;
    }

    public void setAttribute(String attribute, Object value) {};

    public abstract Color getBackgroundColor();

    public abstract Color getCellColor(Creature o);

    public abstract Color getHeadColor(Creature o);

    public abstract Color getTailColor(Creature o);

    public abstract Color getGenomeBackgroundColor(Creature o);
}
