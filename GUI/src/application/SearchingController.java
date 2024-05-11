package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchingController {

	private ObservableList<String> listPresent = FXCollections.observableArrayList();

	@FXML
	private TextField myTextField;

	@FXML
	private ListView<String> myListView;

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

	}

	@FXML
	public void displaySelectedArticle(MouseEvent e) {
		try {
			Text title = new Text();

			Stage newStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("InformationDisplay.fxml"));
			Scene scene = new Scene(root);

			newStage.setScene(scene);
			newStage.setTitle("New Stage");
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
