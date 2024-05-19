package project.datarefining;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParsingHelper {
    static List<String> extractTags(JsonArray tagsArray) {
        List<String> tags = new ArrayList<>();
        for (JsonElement element : tagsArray) {
            tags.add(element.getAsString());
        }
        return tags;
    }

    // Add a method to determine article type based on member 2's logic (replace with actual implementation)
    static String extractWebsiteSourceFromUrl(String url) {
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

    static String determineArticleType(String url) {
        String websiteSource = extractWebsiteSourceFromUrl(url);
        return switch (websiteSource) {
            case "Twitter" -> "Tweet";
            case "Cointelegraph", "Wired" -> "News Article";
            case "Medium" -> "Blog Post";
            default -> "Unknown"; // Default article type
        };
    }

    static boolean checkContentSimilarity(String title, List<Article> allArticles) {
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

    static String generateUniqueId() {
        // Option 1: Use a UUID (Universally Unique Identifier)
        return UUID.randomUUID().toString();  // This generates a random string ID

    }
}
