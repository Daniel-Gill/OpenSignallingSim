package net.danielgill.oss.railway;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

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
            System.out.println(jo);
        } else {
            return null;
        }

        return r;
    } 
}
