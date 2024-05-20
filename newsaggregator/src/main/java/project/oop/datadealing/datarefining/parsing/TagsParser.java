package project.oop.datadealing.datarefining.parsing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class TagsParser {
    public static List<String> extractTags(JsonArray tagsArray) {
        List<String> tags = new ArrayList<>();
        for (JsonElement element : tagsArray) {
            tags.add(element.getAsString());
        }
        return tags;
    }
}
