package twitter_streaming;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class classPredictor implements MapFunction<Tweet, Tuple2<Tweet, Integer>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Tuple2<Tweet, Integer> map(Tweet tweet) throws Exception, IOException,InterruptedException {
		// TODO Auto-generated method stub
		String postEndpoint = "http://127.0.0.1:5000/model/";
		 
        String inputJson = "{ \"text\":\""+tweet.text+"\" }";
 
        var request = HttpRequest.newBuilder()
            .uri(URI.create(postEndpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        var client = HttpClient.newHttpClient();
 
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.statusCode());
        System.out.println(response.body());
		return new Tuple2<>(tweet,1);
	}	

}
