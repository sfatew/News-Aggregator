package project.datarefining;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String article_link;
    private String website_source;
    private String article_type;
    private String summary;
    private String title;
    private String detailed_content;
    private List<String> tags;
    private String author;
    private String category;
    private String creation_date;

    public Article() {

    }

    public String getArticle_link() {
        return article_link;
    }

    public void setArticle_link(String article_link) {
        this.article_link = article_link;
    }

    public String getWebsite_source() {
        return website_source;
    }

    public void setWebsite_source(String website_source) {
        this.website_source = website_source;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailed_content() {
        return detailed_content;
    }

    public void setDetailed_content(String detailed_content) {
        this.detailed_content = detailed_content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public static Article fromJsonObject(JsonObject jsonObject) {
        Article article = new Article();
        if (jsonObject.has("url")) {
            article.setArticle_link(jsonObject.get("url").getAsString());
        }
        // Extract website source from filename
        String websiteSource = extractWebsiteSourceFromFilename(filename);
        article.setWebsite_source(websiteSource);
        // Fill in logic to determine article type based on member 2's logic (replace 2 with appropriate value)
        article.setArticle_type(determineArticleType(String.valueOf(jsonObject)));
        article.setSummary(jsonObject.get("summary").getAsString());
        article.setTitle(jsonObject.get("title").getAsString());
        if (jsonObject.has("post_content")) {
            article.setDetailed_content(jsonObject.get("post_content").getAsString());
        }
        if (jsonObject.has("tags")) {
            JsonElement tagsElement = jsonObject.get("tags");
            if (tagsElement.isJsonArray()) {
                article.setTags(tagsElement.getAsJsonArray());
            } else {
                // Handle the case where tags is a single string
                List<String> tagsList = new ArrayList<>();
                tagsList.add(tagsElement.getAsString());
                article.setTags(tagsList);
            }
        } else {
            article.setTags(new ArrayList<>());
        }
        if (jsonObject.has("author")) {
            article.setAuthor(jsonObject.get("author").getAsString());
        }
        return article;
    }

    private void setTags(JsonArray asJsonArray) {
    }

    private static String extractWebsiteSourceFromFilename(String filename) {
        // Implement logic to extract website source from filename (e.g., using string manipulation)
        // You can use regular expressions or string splitting based on patterns in your filenames
        // For example:
        if (filename.contains("output_cointelegraph.json")) {
            return "Cointelegraph";
        } else if (filename.contains("output_wired.json")) {
            return "Wired";
        } else if (filename.contains("output_medium.json")) {
            return "Medium";
        } else {
            return "Unknown"; // Default website source
        }
    }

    private static List<String> extractTags(JsonArray tagsArray) {
        List<String> tags = new ArrayList<>();
        for (JsonElement element : tagsArray) {
            tags.add(element.getAsString());
        }
        return tags;
    }

    // Add a method to determine article type based on member 2's logic (replace with actual implementation)
    private static String determineArticleType(String websiteSource) {
        if (websiteSource.equals("Cointelegraph") || websiteSource.equals("Wired")) {
            return "News";
        } else if (websiteSource.equals("Medium")) {
            return "Blog";
        } else {
            return "Unknown"; // Default article type
        }
    }

    // Add a constructor to allow creating Article objects directly
    public Article(String article_link, String website_source, String article_type, String summary, String title,
                   String detailed_content, List<String> tags, String author, String category, String creation_date) {
        this.article_link = article_link;
        this.website_source = website_source;
        this.article_type = article_type;
        this.summary = summary;
        this.title = title;
        this.detailed_content = detailed_content;
        this.tags = tags;
        this.author = author;
        this.category = category;
        this.creation_date = creation_date;
    }
}