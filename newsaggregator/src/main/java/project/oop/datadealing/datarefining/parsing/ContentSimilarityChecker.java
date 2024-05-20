package project.oop.datadealing.datarefining.parsing;

import org.apache.commons.lang3.StringUtils;
import project.oop.datadealing.datarefining.Article;

import java.util.List;

public class ContentSimilarityChecker {
    public static boolean checkContentSimilarity(String title, List<Article> allArticles) {
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
}
