package project.oop.datadealing.datarefining.parsing;

public class SourceParser {
    public static String extractWebsiteSourceFromUrl(String url) {
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
}
