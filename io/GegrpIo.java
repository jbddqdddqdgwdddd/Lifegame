package com.victor.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Gegrp;
import com.victor.model.system.State;
import com.victor.model.system.Creature;
import com.victor.model.seed.SeedFactory.SeedType;
import com.victor.model.utilities.GrowUtility;

//游戏IO：文件保存和数据加载
public class GegrpIo {
    public static int VERSION = 0;
    //保存游戏
    public static void saveGegrp(File file, GameModel gameModel) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("victor Gegrp");
        writer.println("V" + VERSION);
        writer.println();
        saveSetting(writer, gameModel);
        writer.println();
        saveCreatures(writer, gameModel);
        writer.close();
    }
    //加载游戏
    public static void loadGegrp(File file, GameModel gameModel, int colorKind) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(isr);
        int version = loadVersion(reader);
        loadSetting(reader, gameModel);
        clearColumn(gameModel, colorKind);
        loadCreatures(reader, gameModel, colorKind);
        reader.close();
    }
    //保存设定
    private static void saveSetting(PrintWriter writer, GameModel gameModel) {
        Settings s = gameModel.getSetting();
        writer.println(Settings.SEED_TYPE + " : " + s.getString(Settings.SEED_TYPE));
        writer.println(Settings.LIFE_MODE + " : " + s.getString(Settings.LIFE_MODE));
        writer.println(Settings.MAX_LIFESPAN + " : " + s.getInt(Settings.MAX_LIFESPAN));
        writer.println(Settings.TARGET_LIFESPAN + " : " + s.getInt(Settings.TARGET_LIFESPAN));
        writer.println(Settings.CHILD_ONE_PARENT_AGE + " : " + s.getInt(Settings.CHILD_ONE_PARENT_AGE));
        writer.println(Settings.MUTATION_RATE + " : " + s.getInt(Settings.MUTATION_RATE));
        writer.println(Settings.SPROUT_DELAYED_MODE + " : " + s.getBoolean(Settings.SPROUT_DELAYED_MODE));
        writer.println(Settings.PRIMARY_HUE_DEGREES + " : " + s.getInt(Settings.PRIMARY_HUE_DEGREES));
        writer.println(Settings.HUE_RANGE + " : " + s.getInt(Settings.HUE_RANGE));
    }
    //保存生命
    private static void saveCreatures(PrintWriter writer, GameModel gameModel) throws IOException {
        int orgCount = gameModel.getSystem().getCreatures().size();
        int saveOrgCount = Math.min(20, orgCount);
        writer.println("Saved Creatures" + " : " + saveOrgCount);

        ArrayList<Creature> orgList = new ArrayList<>(gameModel.getSystem().getCreatures());
        Collections.shuffle(orgList);

        for (int i = 0; i < saveOrgCount; i++) {
            Creature o = orgList.get(i);
            writer.println();
            writer.println("lifespan : "+o.lifespan);
            for (int age = 0; age <= o.lifespan; age++) {
                if (o.getGegrp() == null || o.getGegrp().getStatesAtAge(age) == null) {
                    continue;
                }
                for (State m : o.getGegrp().getStatesAtAge(age)) {
                    Point p = m.getLocation();
                    writer.println(age + ", " + p.x + ", " + p.y);
                }
            }
        }
    }
    //加载版本
    private static int loadVersion(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.startsWith("V")) {
            line = reader.readLine();
        }
        if (line == null || !line.startsWith("V")) {
            return -1;
        }
        try {
            return Integer.parseInt(line.substring(1));
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
    //加载设定
    private static void loadSetting(BufferedReader reader, GameModel gameModel) throws IOException {
        String line = reader.readLine();
        while (line != null && (!line.contains(":") || line.trim().equalsIgnoreCase("setting"))) {
            line = reader.readLine();
        }
        if (line == null) {
            return;
        }
        if (line.trim().equalsIgnoreCase("setting")) {
            line = reader.readLine();
        }
        while (true) {
            if (line == null || line.trim().isEmpty()) {
                return;
            }
            String[] kv = line.split(" : ");
            if (kv.length != 2) {
                throw new IOException("Error parsing organism setting");
            }

            String k = kv[0];
            String v = kv[1];
            gameModel.getSetting().set(k, v);
            line = reader.readLine();
        }
    }
    //清楚列
    private static void clearColumn(GameModel gameModel, int colorKind) {
        Collection<Creature> organisms = new ArrayList<>(gameModel.getSystem().getCreatures());
        int boardWidth = gameModel.getSystem().getBoard().getWidth();

        int minX = colorKind*boardWidth/3;
        int maxX = (colorKind+1)*boardWidth/3;
        for (Creature o: organisms) {
            if (o.x>=minX && o.x<=maxX) {
                gameModel.getSystem().justdieCreature(o);
            }
        }
    }
    //加载生命
    private static void loadCreatures(BufferedReader reader, GameModel gameModel, int colorKind) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.startsWith("Saved Creatures")) {
            line = reader.readLine();
        }
        if (line == null) {
            return;
        }

        int orgCount = Integer.valueOf(line.split(" : ")[1]);
        reader.readLine();
        for (int oi = 0; oi < orgCount; oi++) {
            Map<String, String> attributes = new HashMap<>();
            String followingLine = loadCreatureAttributes(reader, attributes);

            List<State> gegrp = loadCreatureGegrp(reader, followingLine);
            Creature o = addCreatureToBoard(gameModel, colorKind);

            if (o != null) {
                setCreatureAttributes(o, attributes);
                o.getAttributes().colorKind = colorKind;
                Gegrp g = o.getGegrp();
                for (State m : gegrp) {
                    g.addState(m);
                }
            }
        }
    }
    //加载生命属性
    private static String loadCreatureAttributes(BufferedReader reader, Map<String, String> attributes) throws IOException {
        String line = reader.readLine();
        if (line == null || line.trim().equals("") || (line.contains(",") && !line.contains(":"))) {
            return line;
        }
        if (!line.contains(":")) { // 向后兼容
            String lifespan = line.trim();
            attributes.put("lifespan", lifespan);
            return reader.readLine();
        }
        while (line.contains(":")) {
            String [] kv = line.split(":");
            attributes.put(kv[0].trim(), kv[1].trim());
            line = reader.readLine();
        }
        // 返回最后一个line
        return line;
    }
    //创建生命属性
    private static void setCreatureAttributes(Creature o, Map<String, String> attributes) {
        for (String key: attributes.keySet()) {
            String value = attributes.get(key);
            try {
                switch (key) {
                    case "lifespan": o.setLifespan(Integer.parseInt(value)); break;
                }
            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }
    //加载生命组
    private static List<State> loadCreatureGegrp(BufferedReader reader, String firstLine) throws IOException {
        List<State> gegrp = new ArrayList<>();
        String line = firstLine;
        while (line != null && !line.equals("")) {
            String[] tokens = line.split(",");
            Point p = new Point(Integer.valueOf(tokens[1].trim()), Integer.valueOf(tokens[2].trim()));
            int age = Integer.valueOf(tokens[0].trim());
            gegrp.add(new State(p, age, 0));
            line = reader.readLine();
        }
        return gegrp;
    }

    private static Creature addCreatureToBoard(GameModel gameModel, int colorKind) {
        String seedTypeName = gameModel.getSetting().getString(Settings.SEED_TYPE);
        SeedType seedType = SeedType.get(seedTypeName);

        int boardWidth = gameModel.getSystem().getBoard().getWidth();
        int x = (new Random()).nextInt(boardWidth/3);
        int y = (new Random()).nextInt(gameModel.getSystem().getBoard().getHeight());
        x+= colorKind*boardWidth/3;

        Creature o = GrowUtility.growRandomSeed(seedType, gameModel.getSystem(), new Point(x,y));
        return o;
    }
}
