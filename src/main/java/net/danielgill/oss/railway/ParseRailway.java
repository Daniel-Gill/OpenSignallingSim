package net.danielgill.oss.railway;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import net.danielgill.oss.block.Block;
import net.danielgill.oss.block.SignalBlock;
import net.danielgill.oss.block.TwoWaySignalBlock;
import net.danielgill.oss.signal.FourAspectSignal;
import net.danielgill.oss.signal.Signal;
import net.danielgill.oss.ui.Direction;

public class ParseRailway {
    public static Railway parseRailway(File file) throws IOException, JsonException {
        Railway r = new Railway();

        Scanner sc = new Scanner(file);
        String all = "";
        while (sc.hasNextLine()) {
            all += sc.nextLine();
        }
        Object o = Jsoner.deserialize(all);
        if(o instanceof JsonObject) {
            JsonObject jo = (JsonObject) o;

            JsonArray a = (JsonArray) jo.get("blocks");
            for(int i = 0; i < a.size(); i++) {
                JsonObject block = (JsonObject) a.get(i);
                r.addBlock(parseBlock(block));
            }

        } else {
            sc.close();
            return null;
        }

        sc.close();
        return r;
    } 

    private static Block parseBlock(JsonObject jo) {
        String type = (String) jo.get("type");

        if(type.equalsIgnoreCase("signal")) {
            String id = (String) jo.get("id");
            int x = ((BigDecimal) jo.get("x")).intValue();
            int y = ((BigDecimal) jo.get("y")).intValue();
            Direction direction = Direction.getFromString((String) jo.get("direction"));

            JsonArray a = (JsonArray) jo.get("signals");
            Block b = new SignalBlock(id, x, y, direction, parseSignal((JsonObject) a.get(0)));
            return b;
        }

        if(type.equalsIgnoreCase("twoway")) {
            String id = (String) jo.get("id");
            int x = ((BigDecimal) jo.get("x")).intValue();
            int y = ((BigDecimal) jo.get("y")).intValue();
            Direction direction = Direction.getFromString((String) jo.get("direction"));

            JsonArray a = (JsonArray) jo.get("signals");
            Block b = new TwoWaySignalBlock(id, x, y, direction, parseSignal((JsonObject) a.get(0)), parseSignal((JsonObject) a.get(1)));
            return b;
        }

        return null;
    }

    private static Signal parseSignal(JsonObject jo) {
        String type = (String) jo.get("type");

        if(type.equalsIgnoreCase("4AT")) {
            int xOffset = ((BigDecimal) jo.get("xOffset")).intValue();
            int yOffset = ((BigDecimal) jo.get("yOffset")).intValue();
            return new FourAspectSignal(xOffset, yOffset);
        }

        return null;
    }
}
