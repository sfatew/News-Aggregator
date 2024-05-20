package project.oop.datadealing.datastoring;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CreateJSONFile {

	private String inputFilePath; //Path input Json file
	private String outputDirectory; //Path ouput n Json files


	public CreateJSONFile(String outputDirectory) {
		this.inputFilePath = "data\\final_articles.json";
		this.outputDirectory = outputDirectory;
	}

	public CreateJSONFile(String inputFilePath, String outputDirectory) {
		this.inputFilePath = inputFilePath;
		this.outputDirectory = outputDirectory;
	}


	public void createJSONFiles(){
		
		
		//Create ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			//Read Original JSON File and create a Mapping list of articles
			List<Article> articles = objectMapper.readValue(new File(this.inputFilePath),
					new TypeReference<List<Article>>() {});
			//
			for (Article article : articles) {
				String ouputFilePath = this.outputDirectory + article.getId() + ".json";
				objectMapper.writeValue(new File(ouputFilePath), article);
			}
			System.out.println("Articles split and saved successfully...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}