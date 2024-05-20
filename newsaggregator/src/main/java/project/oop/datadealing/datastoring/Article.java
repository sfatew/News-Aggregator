package project.oop.datadealing.datastoring;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {
	@JsonProperty("id")
	private String id;
	@JsonProperty("article_link")
	private String article_link;
	@JsonProperty("website_source")
	private String website_source;
	@JsonProperty("article_type")
	private String article_type;
	@JsonProperty("summary")
	private String summary;
	@JsonProperty("title")
	private String title;
	@JsonProperty("detailed_content")
	private String detailed_content;
	@JsonProperty("creation_date")
	private String creation_date;
	@JsonProperty("tags")
	private String[] tags;
	@JsonProperty("author")
	private String author;
	@JsonProperty("category")
	private String category;
    
	public Article() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

}