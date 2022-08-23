package twitter_streaming;

import java.io.IOException;
import java.util.List;

import org.apache.flink.api.common.functions.MapFunction;



/**
* This class provides pre-processing for text strings based on the Terrier IR platform.
* In particular, it provides an out-of-the-box function for performing stopword removal
* and stemming on a text string.
* 
* Derived from a TextPreProcessor class provided as utility for COMPSCI5088 Big Data: Systems, Programming and Management Coursework.
*
*/

public class TweetPreProcessor implements MapFunction<Tweet, Tweet> {


	private static final long serialVersionUID = -8881625632273118782L;
	private transient TextPreProcessor processor;

	@Override
	public Tweet map(Tweet tweet) throws Exception{
		String text = tweet.text;
//		System.out.println("Pre-processing"+text);
		
		if (processor==null) processor = new TextPreProcessor();
		List<String> tokens = processor.process(text);
		
		StringBuilder builder = new StringBuilder();  
		for (String st : tokens) {  
            builder.append(st);
            builder.append(" ");
        }  
		
		tweet.processedTweet =  builder.toString(); 

//		System.out.println(tweet.processedTweet);
		return tweet;
	}


}
