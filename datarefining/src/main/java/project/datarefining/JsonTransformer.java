package project.datarefining;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTransformer {

    public static List<Article> transformArticles(String filePath1, String filePath2, String filePath3) throws IOException {
        List<Article> articles = new ArrayList<>();

        // Read JSON files from member 1
        articles.addAll(readArticlesFromFile(filePath1));
        articles.addAll(readArticlesFromFile(filePath2));
        articles.addAll(readArticlesFromFile(filePath3));

        return articles;
    }

    private static List<Article> readArticlesFromFile(String filePath) throws IOException {
        List<Article> articles = new ArrayList<>();
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(filePath));
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        reader.close();

        for (JsonElement element : jsonArray) {
            articles.add(Article.fromJsonObject(element.getAsJsonObject()));
        }

        return articles;
    }
}

