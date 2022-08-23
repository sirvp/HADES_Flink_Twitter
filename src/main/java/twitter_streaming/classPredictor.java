package twitter_streaming;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.flink.api.common.functions.MapFunction;

public class classPredictor implements MapFunction<Tweet, Tweet> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Tweet map(Tweet tweet) throws Exception, IOException,InterruptedException {
		// TODO Auto-generated method stub
		String postEndpoint = "http://127.0.0.1:5000/model/";
		 
        String inputJson = "{ \"text\":\""+tweet.processedTweet+"\" }";
 
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(postEndpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        HttpClient client = HttpClient.newHttpClient();
 
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        
//        System.out.println(response.statusCode());
        String prediction = response.body();
        tweet.predictedCategory = Integer.parseInt(prediction.substring(14,15));
        
//        System.out.println(response.body());
		return tweet;
	}	

}
