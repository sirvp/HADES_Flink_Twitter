package twitter_streaming;

import java.io.IOException;
import java.io.Serializable;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

/* Tweet Object to store attributes of tweets obtained from Twitter API
 * 
 */
public class Tweet implements Serializable {
	
	private static final long serialVersionUID = 8501833888342582575L;
	
		String text;
	    private String userName;
	    public String rawText;
	    public String lang;
	    public String id;
	    public String location;
	    public String processedTweet;
	    public Integer predictedCategory;
	   
		public Tweet() {
	    }

	    public static Tweet fromString(String s) {

	        ObjectMapper jsonParser = new ObjectMapper();
	        Tweet tweet = new Tweet();
	        tweet.rawText = s;

	        try {
	            JsonNode node = jsonParser.readValue(s, JsonNode.class);
	            Boolean isLang = node.has("user") && node.get("user").has("lang");

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

	    public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getRawText() {
			return rawText;
		}

		public void setRawText(String rawText) {
			this.rawText = rawText;
		}

		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getProcessedTweet() {
			return processedTweet;
		}

		public void setProcessedTweet(String processedTweet) {
			this.processedTweet = processedTweet;
		}
		public Integer getPredictedCategory() {
			return predictedCategory;
		}

		public void setPredictedCategory(Integer predictedCategory) {
			this.predictedCategory = predictedCategory;
		}

		@Override
	    public String toString() {
	        return 
	            this.userName+";"
    			+this.text + "; "
	        	+this.predictedCategory + " ";
	    }
}
