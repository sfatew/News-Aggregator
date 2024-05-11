package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

			String tt = listDetailPresent.get(indexOfList).get(7);
			Text title = new Text(tt);

			Text idList = new Text(String.valueOf(indexOfList));
//			title.setFont(Font.font("Palatino", 45));
//			title.setWrappingWidth(600);
//			title.setStyle("-fx-underline: true;");

//			Text tags = new Text("Tags: " + listDetailPresent.get(indexOfList).get(10));

			AnchorPane root = new AnchorPane();
			root.getChildren().addAll(title, idList);

			AnchorPane.setTopAnchor(title, 40.0);
			AnchorPane.setLeftAnchor(title, 30.0);

			AnchorPane.setTopAnchor(idList, 40.0);
			AnchorPane.setLeftAnchor(idList, 700.0);

			Scene scene = new Scene(root, 900, 600);

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
