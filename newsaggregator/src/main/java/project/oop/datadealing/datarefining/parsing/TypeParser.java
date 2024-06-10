package project.oop.datadealing.datarefining.parsing;

import static project.oop.datadealing.datarefining.parsing.SourceParser.extractWebsiteSourceFromUrl;

public class TypeParser {
    public static String determineArticleType(String url) {
        String websiteSource = extractWebsiteSourceFromUrl(url);
        return switch (websiteSource) {
            case "Twitter" -> "Tweet";
            case "Cointelegraph", "Wired" -> "News Article";
            case "Medium" -> "Blog Post";
            default -> "Unknown"; // Default article type
        };
    }
}