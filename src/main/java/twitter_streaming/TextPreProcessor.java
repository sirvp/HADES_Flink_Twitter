package twitter_streaming;

import java.util.ArrayList;
import java.util.List;

import org.terrier.indexing.tokenisation.Tokeniser;
import org.terrier.terms.BaseTermPipelineAccessor;

/**
 * This class provides pre-processing for text strings based on the Terrier IR platform.
 * In particular, it provides an out-of-the-box function for performing stopword removal
 * and stemming on a text string.
 * 
 * Derived from a TextPreProcessor class provided as utility for COMPSCI5088 Big Data: Systems, Programming and Management Coursework.
 *
 */
public class TextPreProcessor {

	
	BaseTermPipelineAccessor termProcessingPipeline; // processes an individual term
	Tokeniser tokeniser; // splits a string into multiple terms
	
	/**
	 * Default Constructor
	 */
	public TextPreProcessor() {
		
		termProcessingPipeline = new BaseTermPipelineAccessor("Stopwords");
		tokeniser = Tokeniser.getTokeniser();
		
	}
	
	/**
	 * Returns an array of processed terms for an input text string
	 * @param text
	 * @return
	 */
	public List<String> process(String text) {
		String[] inputTokens = tokeniser.getTokens(text);
		
		if (inputTokens==null) return new ArrayList<String>(0);
		
		List<String> outTokens = new ArrayList<String>(inputTokens.length);
		for (int i =0; i<inputTokens.length; i++) {
			String processedTerm = termProcessingPipeline.pipelineTerm(inputTokens[i]);
			if (processedTerm==null) continue;
			outTokens.add(processedTerm);
		}
		
		return outTokens;
	}
	
}