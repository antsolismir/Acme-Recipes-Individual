package acme.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acme.entities.systemConfiguration.SystemConfiguration;

public class SpamDetector {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;
	
	// Methods
	
	private SpamDetector() {}
	
	public static Boolean isSpam(final String textToCheck, final SystemConfiguration systemConfiguration) {
		
		boolean result = false;
		
		final List<String> wordsChecking = SpamDetector.getWords(textToCheck);		
		final Map<String,Double> esSpamTerms = SpamDetector.getSpamWords(systemConfiguration.getSpamTermsEs());		
		final Map<String,Double> enSpamTerms = SpamDetector.getSpamWords(systemConfiguration.getSpamTermsEn());
		final Map<String,Double> spamTerms = new HashMap<>();
		spamTerms.putAll(esSpamTerms);
		spamTerms.putAll(enSpamTerms);
		
		final Double spamThreshold = systemConfiguration.getSpamThreshold();
		
		final Double spamRatio = SpamDetector.calculateSpamRatio(wordsChecking, spamTerms);
		
		if(spamRatio >= spamThreshold) {
			result = true;
		}
		
		return result;
	}
	
	private static List<String> getWords(final String originalText){
		return Arrays.asList(originalText.replaceAll("[.,:;/*=|()¡!¿?{}`´<>]"," ")
			.replace("\""," ")
			.replace("\\"," ")
			.replace("["," ")
			.replace("]"," ")
			.trim()
			.split("\\s+"));		
	}
	
	private static Map<String,Double> getSpamWords(final String spamTermsArray){
		
		final Map<String,Double> spamWords = new HashMap<String,Double>();
		
		for(final String keyValue : spamTermsArray.split(",")) {
			final String[] pair = keyValue.replace("("," ")
				.replace(")"," ")
				.replace("'", "")
				.trim()
				.split(":");
			spamWords.put(pair[0], Double.valueOf(pair[1]));
			}
		
		return spamWords;
	}
	
	private static Double calculateSpamRatio(final List<String> wordsChecking, final Map<String,Double> spamTerms) {
		
		Double totalSpamWeight = 0.;
		
		for(final String word: wordsChecking) {
			if(spamTerms.keySet().contains(word.toLowerCase())) {
				totalSpamWeight+=spamTerms.get(word.toLowerCase());
			}
		}
		
		return totalSpamWeight/wordsChecking.size();
	}	
}
