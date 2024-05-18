package project.datarefining;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePath1 = "data/output_cointelegraph.json";   // Replace with actual file paths
        String filePath2 = "data/output_medium.json";
        String filePath3 = "data/output_wired.json";

        List<Article> articles = JsonTransformer.transformArticles(filePath1, filePath2, filePath3);

        Gson gson = new Gson();
        String finalJson = gson.toJson(articles);

        // Write the final JSON to a file (optional)
        Writer writer = new FileWriter("data/final_articles.json");
        writer.write(finalJson);
        writer.close();

        System.out.println("Final JSON file created successfully!");
    }
}


