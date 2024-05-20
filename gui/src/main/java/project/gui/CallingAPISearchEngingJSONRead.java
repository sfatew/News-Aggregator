package project.gui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CallingAPISearchEngingJSONRead {

	private final String BASE_URL = "https://vtqn-search-engine-75080fd33305.herokuapp.com/search=";
	private String present = "";
	private ObservableList<String> listPresent = FXCollections.observableArrayList();
	private List<String> idList = new ArrayList<String>();

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

	public void getTitleId(String query) {
		String strJson = getJSONFromURL(BASE_URL + query + "/100/0");
		try {
			JSONParser parser = new JSONParser();
			Object object = parser.parse(strJson);
			JSONArray mainArrayJsonObject = (JSONArray) object;

			for (int i = 1; i < mainArrayJsonObject.size(); i++) {
				ObservableList<String> items = FXCollections.observableArrayList();
				present = present + (i) + ". ";

				JSONObject jsonResult = (JSONObject) mainArrayJsonObject.get(i);

				String index = (String) jsonResult.get("id");
				items.add(index);

				String title = (String) jsonResult.get("titles");
				items.add(title);

				present = present + title;
				listPresent.add(present);
				present = "";

				idList.add(index);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<String> getListPresent() {
		return listPresent;
	}

	public List<String> getidList() {
		return idList;
	}

}
