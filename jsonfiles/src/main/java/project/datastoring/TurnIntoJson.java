package project.datastoring;

public class TurnIntoJson {

	public static void turnIntoJson() {
		CreateJSONFile createJSONFile = new CreateJSONFile(
				"E:\\University\\Kì 2023.2\\OOP\\Project\\News-Aggregator(1)\\data\\final_articles.json",
				"E:\\University\\Kì 2023.2\\OOP\\Project\\News-Aggregator(1)\\data\\JSONFilesStorage\\");
		createJSONFile.createJSONFiles();
	}

	public static void main(String[] args) {
		turnIntoJson();
	}
}
