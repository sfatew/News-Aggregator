package project.datacollecting.seleniumhelper;

import java.io.*;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class StoringHelper {



    public static JSONArray parseringArray(File f){
        JSONParser jsonParser = new JSONParser();
        
        JSONArray jsonArray = new JSONArray();
        try {
            if (f.exists() && !f.isDirectory()){
                // System.out.println(".");
                Object obj = jsonParser.parse(new FileReader(f));
                // System.out.println(obj);
                jsonArray = (JSONArray)obj;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        // System.out.println(jsonArray);
        return jsonArray;
    }

    @SuppressWarnings("null")
    public static void writeJSON(JSONArray jsonArray, File f){
        FileWriter file = null;
        try {
            file = new FileWriter(f);
            file.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("added to JSON file: " + jsonArray.size());
    }


}
