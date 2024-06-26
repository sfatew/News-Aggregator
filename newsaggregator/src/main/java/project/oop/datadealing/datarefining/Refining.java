package project.oop.datadealing.datarefining;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Refining {

    public static void refining() throws IOException {

        String filePath1 = "data\\output_cointelegraph.json";   // Replace with actual file paths
        String filePath2 = "data\\output_medium.json";
        String filePath3 = "data\\output_wired.json";
        String filePath4 = "data\\output_twitter.json";

        List<Article> articles = JsonTransformer.transformArticles(filePath1, filePath2, filePath3, filePath4);

        Gson gson = new Gson();
        String finalJson = gson.toJson(articles);

        // Specify the desired path for the output file (replace with your actual path)
        String outputFilePath = "data\\final_articles.json";

        // Write the final JSON to the specified file
        Writer writer = new FileWriter(outputFilePath);
        writer.write(finalJson);
        writer.close();

        System.out.println("Final JSON file created successfully!");
    }
}


