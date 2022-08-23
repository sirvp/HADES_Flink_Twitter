package twitter_streaming;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.flink.api.common.functions.MapFunction;

@SuppressWarnings("serial")
public class spacyPreprocessor implements MapFunction<Tweet, Tweet> {

	@Override
	public Tweet map(Tweet tweet) throws Exception,IOException,InterruptedException {
		// TODO Auto-generated method stub

		String postEndpoint = "http://127.0.0.1:5000/preprocessing/";
		System.out.println(tweet.processedTweet);
        String inputJson = "{ \"text\":\""+tweet.processedTweet+"\" }";
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(postEndpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        HttpClient client = HttpClient.newHttpClient();
 
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        
        System.out.println(response.statusCode());
        String preprocessedText = response.body();
        System.out.println(preprocessedText);
        tweet.processedTweet= preprocessedText.substring(16);
		return tweet;
	}

}
