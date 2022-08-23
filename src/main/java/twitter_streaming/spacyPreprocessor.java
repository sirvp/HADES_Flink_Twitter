package twitter_streaming;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * Map Function to Pre-Process Tweets using the Spacy Text Processing Pipeline created for Model Training.
 * Function not used in current iteration.
 */


@SuppressWarnings("serial")
public class spacyPreprocessor implements MapFunction<Tweet, Tweet> {

	@Override
	public Tweet map(Tweet tweet) throws Exception,IOException,InterruptedException {
		
		//Server URL 
		String postEndpoint = "http://127.0.0.1:5000/preprocessing/";
		
		//Creating input JSON String
		String inputJson = "{ \"text\":\""+tweet.processedTweet+"\" }";
        
		//Building HTTP POST Request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(postEndpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        HttpClient client = HttpClient.newHttpClient();
 
        //Collecting Response to POST Request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        //Obtaining Processed Tweet
        String preprocessedText = response.body();
        tweet.processedTweet= preprocessedText.substring(16);
		return tweet;
	}

}
