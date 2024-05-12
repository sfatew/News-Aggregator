package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

public class SearchingController {

	private ObservableList<String> listPresent = FXCollections.observableArrayList();
	private ObservableList<ObservableList<String>> listDetailPresent = FXCollections.observableArrayList();

	@FXML
	private TextField myTextField;

	@FXML
	private ListView<String> myListView;

	@FXML
	private Text title = new Text();

	public void search(ActionEvent e) throws IOException, InterruptedException {
		CallingAPISearchEngingJSONRead searchEngine = new CallingAPISearchEngingJSONRead();

		// get data
		String data = myTextField.getText();
		data = data.replaceAll("\\s", "");
		searchEngine.getResponse(data);

		// present data
		myListView.refresh();
		if (listPresent != null) {
			listPresent.removeAll(listPresent);
		}

		listPresent = searchEngine.getListPresent();
		myListView.setItems(listPresent);
		myListView.setVisible(true);

		listDetailPresent = searchEngine.getListDetailPresent();

	}

	@FXML
	public void displaySelectedArticle(MouseEvent e) {
		try {
			int indexOfList = myListView.getSelectionModel().getSelectedIndex();

			Stage newStage = new Stage();

			// Set title
			String tt = listDetailPresent.get(indexOfList).get(7);
			Text title = new Text(tt);
//			Text idList = new Text(String.valueOf(indexOfList));
			title.setFont(Font.font("Palatino", FontWeight.BOLD, 45));
			title.setWrappingWidth(960);
			title.setStyle("-fx-underline: true;");

			// Tags
			Text tags = new Text("Tags: " + listDetailPresent.get(indexOfList).get(9));
			tags.setFont(Font.font("arial", 20));

			// Author
			Text author = new Text("Author: " + listDetailPresent.get(indexOfList).get(6));
			author.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 18));

			// Date
			Text date = new Text(listDetailPresent.get(indexOfList).get(4));
			date.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.ITALIC, 18));

			// Summary
			Text summary = new Text("\t" + listDetailPresent.get(indexOfList).get(3));
			summary.setWrappingWidth(900);
			summary.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 18));

			// Detail
			Text detail = new Text(listDetailPresent.get(indexOfList).get(8));
			detail.setWrappingWidth(900);
			detail.setFont(Font.font("\t" + "Arial", 22));

			// link
			Text link = new Text(listDetailPresent.get(indexOfList).get(10));

			// Get object for anchorPane
			AnchorPane anchorPane = new AnchorPane();
			anchorPane.getChildren().addAll(title, tags, author, date, summary, detail, link);

			// Set position of title
			AnchorPane.setTopAnchor(title, 50.0);
			AnchorPane.setLeftAnchor(title, 30.0);

			// Set position of tags
			AnchorPane.setTopAnchor(tags, 20.0);
			AnchorPane.setLeftAnchor(tags, 30.0);

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
		myListView.setVisible(false);
	}

}
