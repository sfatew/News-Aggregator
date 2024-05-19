package pck.scraping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateJSONFile {

	public static void main(String[] args) {
		//Path input Json file
		String inputFilePath = "C:/Users/rodri/eclipse-workspace/ScrapingWeb/src/pck/scraping/final_articles.json";
		
		//Path ouput n Json files
		String outputDirectory = "C:/Users/rodri/eclipse-workspace/ScrapingWeb/src/pck/JSONFiles/";
		
		//Create ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			//Read Original JSON File and create a Mapping list of articles
			List<Article> articles = objectMapper.readValue(new File(inputFilePath),
					new TypeReference<List<Article>>() {});
			//
			for (Article article : articles) {
				String ouputFilePath = outputDirectory + article.getId() + ".json";
				objectMapper.writeValue(new File(ouputFilePath), article);
			}
			System.out.println("Articles split and saved successfully...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
