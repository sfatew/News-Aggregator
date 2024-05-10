package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class CallingAPISearchEngine {

	private final String BASE_URL = "https://vtqn-search-engine-75080fd33305.herokuapp.com/search="; // /search=Bitcoin
																										// /5/0

	public void getResponse(String query) throws IOException, InterruptedException {

		// create HttpClient
		HttpClient client = HttpClient.newHttpClient();

		// build HTTP GET
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + query + "/5/0")).build();

		// send the request and receive the response
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		// repose content
		System.out.println(response.body());
	}
}