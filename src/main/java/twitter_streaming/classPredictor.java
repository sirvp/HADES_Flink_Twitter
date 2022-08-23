package twitter_streaming;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.flink.api.common.functions.MapFunction;


/**
 * Map Function to Obtain Predicted Class of each Tweet from a pre-trained Classification Model.
 * Function takes in a Tweet object, adds the predicted category and returns the modified Tweet object
 * 
 */

public class classPredictor implements MapFunction<Tweet, Tweet> {

	private static final long serialVersionUID = 1L;

	@Override
	public Tweet map(Tweet tweet) throws Exception, IOException,InterruptedException {

		//Server URL
		String postEndpoint = "http://127.0.0.1:5000/model/";
		 
		//JSOn Input for the POST call
        String inputJson = "{ \"text\":\""+tweet.processedTweet+"\" }";
 
        //Building the HTTP Post request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(postEndpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        HttpClient client = HttpClient.newHttpClient();
        
        //Collecting Response from Server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        String prediction = response.body();
        
        //Converting String type response to Integer type class value
        tweet.predictedCategory = Integer.parseInt(prediction.substring(14,15));
        
		return tweet;
	}	

}
