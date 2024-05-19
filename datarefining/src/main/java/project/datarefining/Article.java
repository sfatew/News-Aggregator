package project.datarefining;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

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

    public Article() {

    }

    private void setID(String id) {
        this.id = id;
    }

    public String getArticle_link() {
        return article_link;
    }

    public void setArticle_link(String article_link) {
        this.article_link = article_link;
    }

    private String getWebsite_source() {
        return website_source;
    }

    public void setWebsite_source(String website_source) {
        this.website_source = website_source;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
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

    public void setDetailed_content(String detailed_content) {
        this.detailed_content = detailed_content;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    private String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    private static final String TARGET_DATE_FORMAT = "yyyy-MM-dd";


    public static Article fromJsonObject(JsonObject jsonObject, Set<String> processedUrls, List<Article> allArticles) {
        Article article = new Article();

        String id = generateUniqueId();
        article.setID(id);

        if (jsonObject.has("url")) {
            article.setArticle_link(jsonObject.get("url").getAsString());
        } else {
            // Handle missing article link (e.g., assign a default value, log a warning)
            System.err.println("Missing article link for article: " + jsonObject);
        }
        // Extract website source from filename

        String url = jsonObject.get("url").getAsString();
        String websiteSource = extractWebsiteSourceFromUrl(url);
        article.setWebsite_source(websiteSource);
        article.setArticle_type(determineArticleType(String.valueOf(jsonObject)));

        if (jsonObject.has("summary")) {
            // Check if "summary" field exists and is not JsonNull
            JsonElement summaryElement = jsonObject.get("summary");
            if (summaryElement != JsonNull.INSTANCE) {
                String summary = summaryElement.getAsString();
                article.setSummary(summary);
            } else {
                article.setSummary(null); // Set summary to null if "summary" field is missing or JsonNull
                System.err.println("Missing summary for article: " + jsonObject); // Or log a warning (optional)
            }
        } else {
            article.setSummary(null); // Set summary to null if "summary" field is missing altogether
            System.err.println("Missing summary for article: " + jsonObject); // Or log a warning (optional)
        }



        if (jsonObject.has("post_content")) {
            article.setDetailed_content(jsonObject.get("post_content").getAsString());
        }

        if (jsonObject.has("tags")) {
            JsonElement tagsElement = jsonObject.get("tags");
            if (tagsElement.isJsonArray()) {
                article.setTags(extractTags(tagsElement.getAsJsonArray()));
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
            String extractedAuthor = jsonObject.get("author") != JsonNull.INSTANCE ? jsonObject.get("author").getAsString() : null;
            article.setAuthor(extractedAuthor);
        }

        if (jsonObject.has("title")) {
            // Check if "title" field exists and is not JsonNull
            JsonElement titleElement = jsonObject.get("title");
            if (titleElement != JsonNull.INSTANCE) {
                String title = titleElement.getAsString();
                article.setTitle(title);
            } else {
                // Set title to "Tweet from" + author if title is missing
                if ("Twitter".equals(article.getWebsite_source())) {
                    article.setTitle("Tweet from " + article.getAuthor());
                } else {
                    article.setTitle(null); // Set title to null for non-Twitter sources
                    System.err.println("Missing title for article: " + jsonObject); // Or log a warning (optional)
                }
            }
        } else {
            article.setTitle(null); // Set title to null if "title" field is missing or JsonNull
            System.err.println("Missing title for article: " + jsonObject); // Or log a warning (optional)
        }

        if (jsonObject.has("date")) {
            // Check if "date" field exists and is not JsonNull
            JsonElement dateElement = jsonObject.get("date");
            if (dateElement != JsonNull.INSTANCE) {
                String dateString = dateElement.getAsString();
                SimpleDateFormat sourceDateFormat;

                // Initialize sourceDateFormat based on websiteSource
                switch (websiteSource) {
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
                        // Handle unknown website source format (e.g., log error)
                        System.err.println("Unknown date format for website: " + websiteSource);
                        article.setCreation_date(null); // Set creation date to null if format unknown
                        return article; // Or throw an exception (optional)
                }

                try {
                    Date parsedDate = sourceDateFormat.parse(dateString);

                    // Format the date to the target format
                    SimpleDateFormat targetDateFormat = new SimpleDateFormat(TARGET_DATE_FORMAT);
                    String formattedDate = targetDateFormat.format(parsedDate);
                    article.setCreation_date(formattedDate);
                } catch (java.text.ParseException e) {
                    // Handle parsing exception (e.g., invalid date format, logging error)
                    System.err.println("Error parsing date for " + websiteSource + ": " + dateString);
                    article.setCreation_date(null); // Set creation date to null on parsing error
                }
            } else {
                article.setCreation_date(null); // Set creation date to null if "date" field is missing or JsonNull
            }
        }


        // Check for duplicate URL
        String dup_url = article.getArticle_link();
        if (processedUrls.contains(dup_url)) {
            System.out.println("Skipping duplicate article (URL): " + dup_url);
            return null; // Or perform some other action (e.g., logging)
        }
        processedUrls.add(dup_url);

        // Optional: Check for duplicate content (if content similarity is desired)
        boolean isDuplicate = article.getTitle() != null && checkContentSimilarity(article.getTitle().toLowerCase(), allArticles);
        if (isDuplicate) {
            System.out.println("Found potential duplicate based on content (URL: " + dup_url + ")");
            // You can decide how to handle potential content duplicates here (e.g., ignore, log further details)
            return null; // Or perform some other action (e.g., return existing article)
        }

        return article;
    }

    private static List<String> extractTags(JsonArray tagsArray) {
        List<String> tags = new ArrayList<>();
        for (JsonElement element : tagsArray) {
            tags.add(element.getAsString());
        }
        return tags;
    }

    // Add a method to determine article type based on member 2's logic (replace with actual implementation)
    private static String extractWebsiteSourceFromUrl(String url) {
        if (url.contains("twitter.com")) {
            return "Twitter";
        } else if (url.contains("cointelegraph.com")) {
            return "Cointelegraph";
        } else if (url.contains("medium.com")) {
            return "Medium";
        } else if (url.contains("wired.com")) {
            return "Wired";
        } else {
            return "Unknown"; // Default website source
        }
    }

    private static String determineArticleType(String url) {
        String websiteSource = extractWebsiteSourceFromUrl(url);
        return switch (websiteSource) {
            case "Twitter" -> "Tweet";
            case "Cointelegraph", "Wired" -> "News Article";
            case "Medium" -> "Blog Post";
            default -> "Unknown"; // Default article type
        };
    }

    private static boolean checkContentSimilarity(String title, List<Article> allArticles) {
        int similarityThreshold = 80; // Adjust based on desired strictness

        for (Article existingArticle : allArticles) {
            String existingTitle = existingArticle.getTitle().toLowerCase();
            int distance = StringUtils.getLevenshteinDistance(title, existingTitle);
            int titleLength = Math.max(title.length(), existingTitle.length());
            double similarity = (1.0 - (double) distance / titleLength) * 100;
            if (similarity >= similarityThreshold) {
                return true; // Titles are similar enough to suggest potential content duplication
            }
        }

        return false; // No significant title similarity found
    }

    private static String generateUniqueId() {
        // Option 1: Use a UUID (Universally Unique Identifier)
        return UUID.randomUUID().toString();  // This generates a random string ID

    }


    // Add a constructor to allow creating Article objects directly
    public Article(String id, String article_link, String website_source, String article_type, String summary, String title,
                   String detailed_content, List<String> tags, String author, String creation_date) {
        this.id = id;
        this.article_link = article_link;
        this.website_source = website_source;
        this.article_type = article_type;
        this.summary = summary;
        this.title = title;
        this.detailed_content = detailed_content;
        this.tags = tags;
        this.author = author;
        this.creation_date = creation_date;
    }

}