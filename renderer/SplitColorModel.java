package com.victor.renderer;


import java.awt.Color;

import com.victor.model.system.Creature;

public class SplitColorModel extends ColorModel {

    @Override
    public Color getBackgroundColor() {
        switch (backgroundTheme) {
            case black:
                return Color.black;
            default:
                return Color.white;
        }
    }

    @Override
    public Color getCellColor(Creature o) {
        if (getBackgroundTheme() == BackgroundTheme.white) {
            int grayC = 140;
            switch (o.getAttributes().colorKind) {
                case 0: return new Color(255, grayC, grayC);
                case 1: return new Color(grayC-80, 255, grayC-80);
                case 2: return new Color(grayC, grayC ,255);
            }
        }
        else {
            int grayC = 100;
            switch (o.getAttributes().colorKind) {
                case 0: return new Color(255, grayC, grayC);
                case 1: return new Color(grayC-10, 255, grayC-10);
                case 2: return new Color(grayC-10, grayC+10 ,255);
            }
        }
        return null;
    }

    @Override
    public Color getHeadColor(Creature o) {
        if (getBackgroundTheme() == BackgroundTheme.white) {
            int grayC = 80;
            switch (o.getAttributes().colorKind) {
                case 0: return new Color(255, grayC, grayC);
                case 1: return new Color(0, 230, 0);
                case 2: return new Color(grayC, grayC, 255);
            }
        }
        else {
            int grayC = 80;
            switch (o.getAttributes().colorKind) {
                case 0: return new Color(255, grayC, grayC);
                case 1: return new Color(0, 230, 0);
                case 2: return new Color(grayC-10, grayC+10, 255);
            }
        }
        return null;
    }

    @Override
    public Color getTailColor(Creature o) {
        return getHeadColor(o);
    }

    @Override
    public Color getGenomeBackgroundColor(Creature o) {
        int grayC = 210;
        switch (o.getAttributes().colorKind) {
            case 0: return new Color(255, grayC-10, grayC-10);
            case 1: return new Color(grayC, 255, grayC);
            case 2: return new Color(grayC, grayC,255);
        }
        return null;
    }
}
