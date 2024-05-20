package project.datarefining;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.*;

import static project.datarefining.parsing.ContentSimilarityChecker.checkContentSimilarity;
import static project.datarefining.parsing.IdGenerator.generateUniqueId;
import static project.datarefining.parsing.SourceParser.extractWebsiteSourceFromUrl;
import static project.datarefining.parsing.TagsParser.extractTags;
import static project.datarefining.parsing.TypeParser.determineArticleType;

public class Article {

    public String id;
    private String article_link;
    private String website_source;
    private String article_type;
    private String summary;
    private String title;
    private String detailed_content;
    private List<String> tags;
    private String author;
    private String creation_date;

    private static final String TARGET_DATE_FORMAT = "yyyy-MM-dd";

    // Default constructor
    public Article() {}

    // Constructor to populate Article fields from JsonObject
    public Article(JsonObject jsonObject, Set<String> processedUrls, List<Article> allArticles) {
        this.id = generateUniqueId();
        
        if (jsonObject.has("url")) {
            this.article_link = jsonObject.get("url").getAsString();
        } else {
            System.err.println("Missing article link for article: " + jsonObject);
        }

        String url = jsonObject.get("url").getAsString();
        this.website_source = extractWebsiteSourceFromUrl(url);
        this.article_type = determineArticleType(String.valueOf(jsonObject));

        if (jsonObject.has("summary")) {
            JsonElement summaryElement = jsonObject.get("summary");
            this.summary = summaryElement != JsonNull.INSTANCE ? summaryElement.getAsString() : null;
            if (this.summary == null) {
                System.err.println("Missing summary for article: " + jsonObject);
            }
        } else {
            this.summary = null;
            System.err.println("Missing summary for article: " + jsonObject);
        }

        if (jsonObject.has("post_content")) {
            this.detailed_content = jsonObject.get("post_content").getAsString();
        }

        if (jsonObject.has("tags")) {
            JsonElement tagsElement = jsonObject.get("tags");
            if (tagsElement.isJsonArray()) {
                this.tags = extractTags(tagsElement.getAsJsonArray());
            } else {
                this.tags = new ArrayList<>();
                this.tags.add(tagsElement.getAsString());
            }
        } else {
            this.tags = new ArrayList<>();
        }

        if (jsonObject.has("author")) {
            this.author = jsonObject.get("author") != JsonNull.INSTANCE ? jsonObject.get("author").getAsString() : null;
        }

        if (jsonObject.has("title")) {
            JsonElement titleElement = jsonObject.get("title");
            if (titleElement != JsonNull.INSTANCE) {
                this.title = titleElement.getAsString();
            } else {
                if ("Twitter".equals(this.website_source)) {
                    this.title = "Tweet from " + this.author;
                } else {
                    this.title = null;
                    System.err.println("Missing title for article: " + jsonObject);
                }
            }
        } else {
            this.title = null;
            System.err.println("Missing title for article: " + jsonObject);
        }

        if (jsonObject.has("date")) {
            JsonElement dateElement = jsonObject.get("date");
            if (dateElement != JsonNull.INSTANCE) {
                String dateString = dateElement.getAsString();
                SimpleDateFormat sourceDateFormat;

                switch (this.website_source) {
                    case "Cointelegraph":
                        sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        break;
                    case "Wired":
                        sourceDateFormat = new SimpleDateFormat("MM.dd.yyyy");
                        break;
                    case "Medium":
                        sourceDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                        break;
                    case "Twitter":
                        sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        break;
                    default:
                        System.err.println("Unknown date format for website: " + this.website_source);
                        this.creation_date = null;
                        return;
                }

                try {
                    Date parsedDate = sourceDateFormat.parse(dateString);
                    SimpleDateFormat targetDateFormat = new SimpleDateFormat(TARGET_DATE_FORMAT);
                    this.creation_date = targetDateFormat.format(parsedDate);
                } catch (java.text.ParseException e) {
                    System.err.println("Error parsing date for " + this.website_source + ": " + dateString);
                    this.creation_date = null;
                }
            } else {
                this.creation_date = null;
            }
        }

        String dup_url = this.article_link;
        if (processedUrls.contains(dup_url)) {
            System.out.println("Skipping duplicate article (URL): " + dup_url);
            return;
        }
        processedUrls.add(dup_url);

        boolean isDuplicate = this.title != null && checkContentSimilarity(this.title.toLowerCase(), allArticles);
        if (isDuplicate) {
            System.out.println("Found potential duplicate based on content (URL: " + dup_url + ")");
            return;
        }
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getArticle_link() {
        return article_link;
    }

    public String getWebsite_source() {
        return website_source;
    }

    public String getArticle_type() {
        return article_type;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getDetailed_content() {
        return detailed_content;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreation_date() {
        return creation_date;
    }

    // Static method to create Article from JsonObject
    public static Article fromJsonObject(JsonObject jsonObject, Set<String> processedUrls, List<Article> allArticles) {
        Article article = new Article(jsonObject, processedUrls, allArticles);
        if (article.getArticle_link() == null || processedUrls.contains(article.getArticle_link())) {
            return null;
        }
        return article;
    }
}
