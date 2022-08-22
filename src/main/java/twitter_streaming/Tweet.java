package twitter_streaming;

import java.io.IOException;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class Tweet {
	
	 	String text;
	    private String userName;
	    public String rawText;
	    public String lang;
	    public String id;
	    public String location;

	    private Tweet() {
	    }

	    public static Tweet fromString(String s) {

	        ObjectMapper jsonParser = new ObjectMapper();
	        Tweet tweet = new Tweet();
	        tweet.rawText = s;

	        try {
	            JsonNode node = jsonParser.readValue(s, JsonNode.class);
	            Boolean isLang = node.has("user") && node.get("user").has("lang");// &&
	            // !node.get("user").get("lang").asText().equals("null");

	            if(isLang && node.has("text"))
	            {
	                JsonNode userNode = node.get("user");
	                tweet.text = node.get("text").asText();
	                
	                tweet.userName = userNode.get("screen_name").asText();
	                tweet.lang = userNode.get("lang").asText();
	                tweet.id = node.get("id_str").asText();
	                tweet.location = userNode.get("location").asText();

	                return tweet;
	            }

	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;

	    }

	    @Override
	    public String toString() {
	        return 
	//      		this.userName+ "; "
    //            +this.lang + "; "
    //           +this.source + "; "
	            this.userName+";"
    			+this.text + "; "
	        	+this.id+";"
	        	+this.location;
	     //   	+this.rawText + " ";
	    }
}
