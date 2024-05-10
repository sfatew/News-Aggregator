package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchingController {
	@FXML
	private TextField myTextField;

	private CallingAPISearchEngine searchEngine = new CallingAPISearchEngine();

	public void search(ActionEvent e) throws IOException, InterruptedException {
		String data = myTextField.getText();
		data = data.replaceAll("\\s", "");
		searchEngine.getResponse(data);
	}

	public void clear(ActionEvent e) {
		myTextField.clear();
	}

}
