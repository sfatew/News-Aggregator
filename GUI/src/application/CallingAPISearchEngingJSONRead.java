package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CallingAPISearchEngingJSONRead {
	private final String BASE_URL = "https://vtqn-search-engine-75080fd33305.herokuapp.com/search=";
	private String present = "";
	private ObservableList<String> listPresent = FXCollections.observableArrayList();

	private ObservableList<ObservableList<String>> listDetailPresent = FXCollections.observableArrayList();

	public String getJSONFromURL(String strUrl) {
		String jsonText = "";

		try {
			@SuppressWarnings("deprecation")
			URL url = new URL(strUrl);
			InputStream is = url.openStream();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonText += line + "\n";
			}
			is.close();
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return jsonText;

	}

	public void getResponse(String query) {
		String strJson = getJSONFromURL(BASE_URL + query + "/5/0");
		try {
			JSONParser parser = new JSONParser();
			Object object = parser.parse(strJson);
			JSONArray mainArrayJsonObject = (JSONArray) object;

			for (int i = 0; i < mainArrayJsonObject.size(); i++) {
				ObservableList<String> items = FXCollections.observableArrayList();
				present = present + (i + 1) + ". ";

				JSONObject jsonResult = (JSONObject) mainArrayJsonObject.get(i);
//				System.out.println("RESULT " + (i + 1) + " :");

				String index = (String) jsonResult.get("_index");
//				System.out.println("index : " + index);
				items.add(index);

				String type = (String) jsonResult.get("_type");
//				System.out.println("type : " + type);
				items.add(type);

				String id = (String) jsonResult.get("_id");
//				System.out.println("id : " + id);
				items.add(id);

				double score = (double) jsonResult.get("_score");
//				System.out.println("score : " + score);

				JSONObject source = (JSONObject) jsonResult.get("_source");
//				System.out.println("source : ");

				String summary = (String) source.get("summary");
//				System.out.println("	summary : " + summary);
				items.add(summary);

				String date = (String) source.get("date");
//				System.out.println("	date : " + date);
				items.add(date);

				String postCover = (String) source.get("post_cover");
//				System.out.println("	Post_cover : " + postCover);
				items.add(postCover);

				String author = (String) source.get("author");
//				System.out.println("	author : " + author);
				items.add(author);

				String title = (String) source.get("title");
//				System.out.println("	title : " + title);
				items.add(title);

				String detailContent = (String) source.get("detailed_content");
//				System.out.println("	detail content : " + detailContent);
				items.add(detailContent);

				JSONArray categoryArray = (JSONArray) source.get("category");
//				System.out.println("	categories: ");
				String tags = "Tags: ";
				for (int j = 0; j < categoryArray.size(); j++) {
					String tagObject = (String) categoryArray.get(j);
//					System.out.println("		" + tagObject);
					tags = tags + "\t" + tagObject;
				}
				items.add(tags);

				String articleLink = (String) source.get("article_link");
//				System.out.println("	article_link : " + articleLink);
				items.add(articleLink);

				present = present + title + "\t\t" + author + "\t\t" + date;
				listPresent.add(present);
				present = "";

				listDetailPresent.add(items);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<String> getListPresent() {
		return listPresent;
	}

	public ObservableList<ObservableList<String>> getListDetailPresent() {
		return listDetailPresent;
	}

	public static void main(String[] args) {

	}
}
