package twitter_streaming;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * FlatMap Function to convert Tweets in JSON String to Tweet object
 */

@SuppressWarnings("serial")
public class TweetParser implements FlatMapFunction<String, Tweet>{

	public void flatMap(String value, Collector<Tweet> collector) throws Exception {
		Tweet tweet = Tweet.fromString(value);
		if (tweet != null) {
			collector.collect(tweet);
		}
	}		
		
}
