package project.oop.datadealing.datarefining;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonTransformer {

    public static List<Article> transformArticles(String filePath1, String filePath2, String filePath3, String filePath4) throws IOException {
        List<Article> articles = new ArrayList<>();
        List<Article> allArticles = new ArrayList<>(); // Create the list here
        // Read JSON files from member 1
        articles.addAll(readArticlesFromFile(filePath1, new HashSet<>(), allArticles)); //create new processed set
        articles.addAll(readArticlesFromFile(filePath2, new HashSet<>(), allArticles));
        articles.addAll(readArticlesFromFile(filePath3, new HashSet<>(), allArticles));
        articles.addAll(readArticlesFromFile(filePath4, new HashSet<>(), allArticles));
        return articles;
    }

    private static List<Article> readArticlesFromFile(String filePath, Set<String> processedUrls, List<Article> allArticles) throws IOException {
        List<Article> articles = new ArrayList<>();
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(filePath));
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        reader.close();

        for (JsonElement element : jsonArray) {
            Article article = Article.fromJsonObject(element.getAsJsonObject(), processedUrls, allArticles);
            if (article != null) {
                articles.add(article);
            }
        }

        return articles;
    }
}


