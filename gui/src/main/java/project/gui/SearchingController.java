package project.gui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static project.datacollecting.Scraping.scraping;
import static project.datarefining.Refining.refining;
import static project.datastoring.Storing.storing;
import static project.loadDataToSearchEngine.LoadDataToServer.loadToServer;


public class SearchingController extends Application {

	private ObservableList<String> listPresent = FXCollections.observableArrayList();
	private ObservableList<String> listDetailPresent = FXCollections.observableArrayList();
	private List<String> idList = new ArrayList<String>();
	private String searchString;
	private String present;
	@FXML
	private TextField myTextField;

	@FXML
	private ListView<String> myListView;

	@FXML
	private Text title = new Text();

	@FXML
	private CheckBox blockchainCheckBox;

	@FXML
	private CheckBox cryptoCheckBox;

	@FXML
	private CheckBox decentralizedFinance;

	@FXML
	private CheckBox adoptation;

	@FXML
	public void search(ActionEvent e) throws IOException, InterruptedException {
		CallingAPISearchEngingJSONRead searchEngine = new CallingAPISearchEngingJSONRead();

		// get data
		String data = myTextField.getText();
		data = data.replaceAll(" ", "");
		searchString = data;

		// if check the blockchain
		if (blockchainCheckBox.isSelected()) {
			searchString += "%20category%3A%23Blockchain";
		}

		// if check the crypto
		if (cryptoCheckBox.isSelected()) {
			searchString += "%20category%3A%23CRYPTOCURRENCY";
		}

		// if check the DeFi
		if (decentralizedFinance.isSelected()) {
			searchString += "%20category%3A%23DeFi";
		}

		// if check the Technology
		if (adoptation.isSelected()) {
			searchString += "%20category%3A%23Adoption";
		}

		// call to searchEngine
		searchEngine.getTitleId(searchString);
		// searchString recover
		searchString = null;

		// present data
		myListView.refresh();
		if (listPresent != null) {
			listPresent.removeAll(listPresent);
		}

		listPresent = searchEngine.getListPresent();
		myListView.setItems(listPresent);
		myListView.getStylesheets().add(getClass().getResource("listview.css").toExternalForm());
		myListView.setVisible(true);

		idList = searchEngine.getidList();

	}

	@FXML
	public void displaySelectedArticle(MouseEvent e) {
		try {
			listDetailPresent.removeAll(listDetailPresent);
			int indexOfList = myListView.getSelectionModel().getSelectedIndex();
			String iD = idList.get(indexOfList);
			System.out.println(iD);

			getResponseFromLocalFile(iD);
			System.out.println(listDetailPresent);

			Stage newStage = new Stage();

			// Set title
			String tt = listDetailPresent.get(4);
			Text title = new Text(tt);
			title.setFont(Font.font("Palatino", FontWeight.BOLD, 45));
			title.setWrappingWidth(960);
			title.setStyle("-fx-underline: true;");

			// Tags
			Text tags = new Text("Tags: " + listDetailPresent.get(7));
			tags.setFont(Font.font("arial", 20));

			// Author
			Text author = new Text("Author: " + listDetailPresent.get(8));
			author.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 18));

			// Date
			Text date = new Text(listDetailPresent.get(6));
			date.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.ITALIC, 18));

			// Summary
			Text summary = new Text("\t" + listDetailPresent.get(3));
			summary.setWrappingWidth(900);
			summary.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 18));

//			 Detail
			Text detail = new Text(listDetailPresent.get(5));
			detail.setWrappingWidth(900);
			detail.setFont(Font.font("\t" + "Arial", 22));

//			 link
			Hyperlink link = new Hyperlink(listDetailPresent.get(1));
			link.setFont(Font.font("Palatino", FontPosture.ITALIC, 18));
			link.setWrapText(true);
			link.setMaxWidth(960);
			link.setOnAction(event -> {
				HostServices hostServices = getHostServices();
				hostServices.showDocument((String) listDetailPresent.get(1));
			});

			// Get object for anchorPane
			AnchorPane anchorPane = new AnchorPane();
			anchorPane.getChildren().addAll(title, tags, author, date, summary, link, detail);

			// Set position of tags
			AnchorPane.setTopAnchor(tags, 20.0);
			AnchorPane.setLeftAnchor(tags, 30.0);

			// Set position of title
			AnchorPane.setTopAnchor(title, AnchorPane.getTopAnchor(tags) + tags.getLayoutBounds().getHeight() + 30);
			AnchorPane.setLeftAnchor(title, 30.0);

			// set position of author
			AnchorPane.setTopAnchor(author, AnchorPane.getTopAnchor(title) + title.getLayoutBounds().getHeight() + 10);
			AnchorPane.setLeftAnchor(author, 30.0);

			// set position of date
			AnchorPane.setTopAnchor(date, AnchorPane.getTopAnchor(title) + title.getLayoutBounds().getHeight() + 10);
			AnchorPane.setLeftAnchor(date, AnchorPane.getLeftAnchor(author) + author.getLayoutBounds().getWidth() + 30);

			// set position of summary
			AnchorPane.setTopAnchor(summary,
					AnchorPane.getTopAnchor(author) + author.getLayoutBounds().getHeight() + 20);
			AnchorPane.setLeftAnchor(summary, 30.0);

			// set position of detail
			AnchorPane.setTopAnchor(detail,
					AnchorPane.getTopAnchor(summary) + summary.getLayoutBounds().getHeight() + 20);
			AnchorPane.setLeftAnchor(detail, 30.0);

			// set position of link
			AnchorPane.setTopAnchor(link, AnchorPane.getTopAnchor(detail) + detail.getLayoutBounds().getHeight() + 20);
			AnchorPane.setLeftAnchor(link, 30.0);

			ScrollPane root = new ScrollPane();
			root.setContent(anchorPane);

			// Create scene
			Scene scene = new Scene(root, 1000, 700);

			newStage.setResizable(false);
			newStage.setScene(scene);
			newStage.setTitle("Detail");
			newStage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void clear(ActionEvent e) {
		myTextField.clear();
		listPresent.removeAll(listPresent);
		idList.removeAll(idList);
		myListView.setVisible(false);
		blockchainCheckBox.setSelected(false);
		cryptoCheckBox.setSelected(false);
		listDetailPresent.removeAll(listDetailPresent);
	}

	public void getResponseFromLocalFile(String iD) throws FileNotFoundException {
		String path = "E:/University/Kì 2023.2/OOP/Project/data/JSONFilesStorage/";
		path += iD;
		path += ".json";
		FileReader strJson = new FileReader(path);
		try {
			JSONParser parser = new JSONParser();
			Object object = parser.parse(strJson);
			JSONObject JsonObject = (JSONObject) object;

			ObservableList<String> items = FXCollections.observableArrayList();

			String id = (String) JsonObject.get("id");
			items.add(id); // 2

			String articleLink = (String) JsonObject.get("article_link");
			items.add(articleLink); // 10

			String type = (String) JsonObject.get("article_type");
			items.add(type); // 1

			String summary = (String) JsonObject.get("summary");
			items.add(summary); // 3

			String title = (String) JsonObject.get("title");
			items.add(title); // 7

			String detailContent = (String) JsonObject.get("detailed_content");
			items.add(detailContent); // 8

			String date = (String) JsonObject.get("creation_date");
			items.add(date); // 4

			JSONArray categoryArray = (JSONArray) JsonObject.get("tags");
			String tags = " ";
			for (int j = 0; j < categoryArray.size(); j++) {
				String tagObject = (String) categoryArray.get(j);
				tags = tags + "\t" + tagObject;
			}
			items.add(tags); // 9

			String author = (String) JsonObject.get("author");
			items.add(author); // 6

			String source = (String) JsonObject.get("website_source");
			items.add(source);

			present = present + title + "_" + author;
			listPresent.add(present);
			present = "";

			listDetailPresent.addAll(items);
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void updateData() throws IOException {

		scraping();
		refining();
		storing();
		loadToServer();
	}

	@Override
	public void start(Stage arg0) throws Exception {

	}

}
