package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchingController {
	private CallingAPISearchEngingJSONRead searchEngine = new CallingAPISearchEngingJSONRead();

	@FXML
	private TextField myTextField;

	@FXML
	private ListView<String> myListView;

	public void search(ActionEvent e) throws IOException, InterruptedException {
		String data = myTextField.getText();
		data = data.replaceAll("\\s", "");
		searchEngine.getResponse(data);
		myListView.setVisible(true);

	}

	public void clear(ActionEvent e) {
		myTextField.clear();
	}

}
