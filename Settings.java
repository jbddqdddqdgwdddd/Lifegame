package com.victor;

import java.util.HashMap;

import com.victor.model.seed.SeedFactory.SeedType;

public class Settings {
    HashMap<String, Object> setting;


    public static String SEED_TYPE = "seedType";    //游戏模式
    public static String LIFE_MODE = "competitiveMode";  //生命模式
    public static String MUTATION_ENABLED = "mutationEnabled";   //翻转模式
    public static String MUTATION_RATE = "mutationRate";   //状态转变速率
    public static String MAX_LIFESPAN = "maxLifespan";   //最大生命周期
    public static String TARGET_LIFESPAN = "targetLifespan";  //目标生命周期
    public static String CHILD_ONE_PARENT_AGE = "minAgeToHaveChildren"; //允许繁衍子代的最小年龄
    public static String CHILD_TWO_PARENT_AGE = "childTwoParentAge";
    public static String CHILD_THREE_PARENT_AGE = "childThreeParentAge";
    //决定是否在检测到种子后立刻执行生长
    public static String SPROUT_DELAYED_MODE = "sproutDelayedMode";
    public static String BACKGROUND_THEME = "backgroundTheme";
    public static String COLOR_MODEL = "colorModel";
    public static String AUTO_SPLIT_COLORS = "autoSplitColors";
    public static String PRIMARY_HUE_DEGREES = "primaryHueDegrees";
    public static String HUE_RANGE = "hueRange";


    public Settings() {
        setting = new HashMap<String, Object>();
        initSetting();
    }

    public void initSetting() {
        set(Settings.SEED_TYPE, SeedType.test.toString());

        set(Settings.LIFE_MODE, "competitive2");

        set(Settings.MUTATION_ENABLED, true);
        set(Settings.MUTATION_RATE, 6); //0-10

        set(Settings.MAX_LIFESPAN, 75);
        set(Settings.TARGET_LIFESPAN, 37);

        set(Settings.CHILD_ONE_PARENT_AGE, 0);
        set(Settings.CHILD_TWO_PARENT_AGE, 0);
        set(Settings.CHILD_THREE_PARENT_AGE, 0);

        set(Settings.SPROUT_DELAYED_MODE, false);
        set(Settings.AUTO_SPLIT_COLORS, true);

        set(Settings.COLOR_MODEL, "AngleColorModel");
        set(Settings.BACKGROUND_THEME, "black");
        set(Settings.PRIMARY_HUE_DEGREES, 0);
        set(Settings.HUE_RANGE, 100);
    }

    public void set(String s, Object o) {
        Object previous = setting.get(s);
        //用于从文件加载设置，根据默认值的类别转换加载的值
        if (previous!=null && o instanceof String) {
            if (previous instanceof Integer) {
                o = Integer.valueOf((String) o);
            }
            if (previous instanceof Boolean) {
                o = Boolean.valueOf((String) o);
            }
        }

        setting.put(s, o);
    }

    public boolean getBoolean(String s) {
        return (Boolean) setting.get(s);
    }

    public int getInt(String s) {
        return (Integer) setting.get(s);
    }

    public String getString(String s) {
        return (String) setting.get(s);
    }
}
