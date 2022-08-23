package twitter_streaming;

import java.util.List;

import org.apache.flink.api.common.functions.MapFunction;



/**
* Map Function to Pre-Process Tweets using the TextPreProcessor class which uses the Terrier IR Platform.
* Tokenising and Stopword Removal performed. 
* Function takes in a Tweet object, processes the tweet text and returns the modified Tweet object
*/


public class TweetPreProcessor implements MapFunction<Tweet, Tweet> {


	private static final long serialVersionUID = -8881625632273118782L;
	private transient TextPreProcessor processor;

	@Override
	public Tweet map(Tweet tweet) throws Exception{
		String text = tweet.text;
		
		if (processor==null) processor = new TextPreProcessor();
		
		// Calling the TextPreProcessor constructor to pre-process tweet.text
		List<String> tokens = processor.process(text);
		
		//Converting the tokenised output back into String
		StringBuilder builder = new StringBuilder();  
		for (String st : tokens) {  
            builder.append(st);
            builder.append(" ");
        }  
		tweet.processedTweet =  builder.toString(); 
		
		return tweet;
	}


}
