/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter_streaming;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */

/* Code References: 
 * [1] https://github.com/haseeb1431/twitter-flink-project
 * [2] https://github.com/aedenj/flink-machine-learning-fish-market-example
*/

public class StreamingJob {

	public static void main(String[] args) throws Exception {
		
		// Set up the streaming execution environment (can be set uo with 
		Configuration config = new Configuration();
		config.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true);
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config);
		
		//Read the twitter keys from config file or environment variable. The values are hardcoded in this particular case.
		Properties twitterCredentials = getTwitterCredentials();
		
		//Add Twitter API as the Data Source
		DataStream<String> tweetStream = env.addSource(new TwitterSource(twitterCredentials));
		
		//Optional Sink to File System. Unused.
//		final StreamingFileSink<Object> sink = StreamingFileSink
//				.forRowFormat(new Path("C:\\Users\\vpsqu\\OneDrive\\Documents\\Work"), new SimpleStringEncoder<>("UTF-8"))
//				.build();

		
		//Flink Transformer Operations on the Tweet object
		tweetStream
				//Flatmap to collect input to Tweet object from JSON input by API
				.flatMap(new TweetParser())
				
				//Optional Filter to Enable Location Specific Detection
				.filter(t-> t.location.contains("United Kingdom"))
				
				//Map Function to Pre-Process Tweets: Tokenizing and removal of stopwords
				.map(new TweetPreProcessor())
				
				//Alternative Pre-Processor which uses Spacy Pipeline implemented in the Python file which trains Classification Model
//				.map(new spacyPreprocessor())
				
				//Map Function to run HttpClient which takes tweet text, runs a POST request to the Flask server and returns prediction results
				.map(new classPredictor())
				
				//Filter to return only the Hateful and Offensive Tweets
				.filter(t -> t.predictedCategory ==0 || t.predictedCategory==1)
				
				//Print output to the console
				.print();

		
		// Execute the program
		env.execute("Flink Streaming Java API Skeleton");
	}



	//Twitter Credentials
	private static Properties getTwitterCredentials(){
		Properties twitterCredentials = new Properties();

		twitterCredentials.setProperty(TwitterSource.CONSUMER_KEY, "Consumer_Key");
		twitterCredentials.setProperty(TwitterSource.CONSUMER_SECRET, "COnsumer-Secret");
		twitterCredentials.setProperty(TwitterSource.TOKEN, "Token");
		twitterCredentials.setProperty(TwitterSource.TOKEN_SECRET, "Token_Secret");
		return  twitterCredentials;
	}
}