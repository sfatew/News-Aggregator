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

public abstract class Article {

    protected String id;
    protected String article_link;
    protected String website_source;
    protected String article_type;
    protected String summary;
    protected String title;
    protected String detailed_content;
    protected List<String> tags;
    protected String author;
    protected String creation_date;

    protected static final String TARGET_DATE_FORMAT = "yyyy-MM-dd";

    public Article() {
        this.id = generateUniqueId();
    }

    // Abstract method to populate fields from JsonObject
    protected abstract void populateFields(JsonObject jsonObject, Set<String> processedUrls, List<Article> allArticles);

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

    // Static factory method to create Article
    public static Article fromJsonObject(JsonObject jsonObject, Set<String> processedUrls, List<Article> allArticles) {
        Article article = new ConcreteArticle(jsonObject, processedUrls, allArticles);
        if (article.getArticle_link() == null || processedUrls.contains(article.getArticle_link())) {
            return null;
        }
        return article;
    }
}
