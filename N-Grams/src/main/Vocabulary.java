package main;

/*import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;*/

import java.io.*;
import java.util.*;

public class Vocabulary {

    private static final Boolean remove_punctuations  = true;
    private static final Boolean debug_mode  = false;
    public static final String sentence_start_token  = "<s>";
    public static final String sentence_end_token  = "</s>";

    // Vocabulary info class. This is responsible to manage vocabularies.
    public class SentenceInfo {
        public String sentence;
        public ArrayList<String> tokens = new ArrayList<String>();
        public ArrayList<String> uniqueTokens = new ArrayList<String>();

        public void setTokens(ArrayList<String> tokens) {
            this.tokens = tokens;

            for (String string : this.tokens) {
                if (!this.uniqueTokens.contains(string)) {
                    this.uniqueTokens.add(string);
                }
            }

            // Add the start and the end tokens.
//            this.tokens.add(0, sentence_start_token);
//            this.tokens.add(sentence_end_token);
//            this.uniqueTokens.add(0, sentence_start_token);
//            this.uniqueTokens.add(sentence_end_token);
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
//            this.sentence = sentence_start_token + " " + this.sentence;
//            this.sentence = this.sentence + " " + sentence_end_token;
        }
    }

    private class TokenInfo {
        private int tokenIndex = -1;
        private int tokenCount = 0;

        @Override
        public boolean equals(Object token) {
            TokenInfo tokenObj = (TokenInfo)token;
            return (this.tokenCount == tokenObj.tokenCount && tokenObj.tokenIndex == this.tokenIndex);
        }
    }

	public class VocabularyInfo {
		private HashMap<String, TokenInfo> map = new HashMap<String, TokenInfo>();
        public HashMap<String, ArrayList<String>> followers = new HashMap<String, ArrayList<String>>();
		public Integer tokenCount = 0;
		private Integer tokenIndex = 0;

		private void updateVocabulary(ArrayList<SentenceInfo>sentenceInfos) {
		    // Update the vocabulary.
            for (SentenceInfo sentenceInfo : sentenceInfos) {
                for (String str : sentenceInfo.tokens) {
                    // Update the tokens
                    String lowerCaseString = str;

                    if (lowerCaseString.trim().length() == 0) {
                        continue;
                    }

                    // Update token count.
                    tokenCount += 1;

                    if (map.containsKey(lowerCaseString)) {
                        TokenInfo token = map.get(lowerCaseString);
                        token.tokenCount += 1;

                        map.replace(lowerCaseString, token);
                    }
                    else {
                        TokenInfo token = new TokenInfo();
                        token.tokenCount = 1;
                        token.tokenIndex = (this.tokenIndex++);

                        map.put(lowerCaseString, token);
                    }
                }

                // Set the followers list.
                for (Integer i = 1; i < sentenceInfo.tokens.size(); i++) {
                    // Update follower hash table.
                    // Get the followers set.
                    String lowerCaseString = sentenceInfo.tokens.get(i);
                    ArrayList<String> list;
                    if (followers.containsKey(lowerCaseString))
                        list = this.followers.get(lowerCaseString); // The word already exists.
                    else
                        list = new ArrayList<String> (); // New word.

                    // Update followers.
                    if (i != 0) {
                        list.add(sentenceInfo.tokens.get(i - 1)); // Word that is being followed.
                    }

                    this.followers.put(lowerCaseString, list);
                }
            }
		}

		public int vocabularySize() {
		    return this.map.size();
        }

		/// Print vocabulary.
		private void printVocabulary() {
            if (debug_mode) {
                Print.print("Vocabulary Size: %d", map.size());
                Print.print("Token Count: %d", tokenCount);

                ArrayList<String> keys = new ArrayList<String>(map.keySet());
                Collections.sort(keys);
                for (String key : keys) {
                    Print.print("%s = %d", key, map.get(key).tokenCount);
                }

                System.out.println(this.followers);
            }
		}
	}

	private File corpus;
    private String filePath;
	public VocabularyInfo info;
    ArrayList<SentenceInfo> sentenceInfoList;

    Vocabulary(File file, String filePath) {
		this.corpus = file;
		this.filePath = filePath;
		this.info = new VocabularyInfo();
	}


    /// Public methods
    public int getFrequencyForToken (String token) {
        return this.info.map.get(token).tokenCount;
    }

	public void createVocabulary() {
		try {
			FileInputStream fileReader = new FileInputStream(this.corpus);
			BufferedReader buf = new BufferedReader(new InputStreamReader(fileReader));
			String line = null;
			StringBuffer strBuf = new StringBuffer();

			while ((line = buf.readLine()) != null) {

				strBuf.append(line);
				parseText(line);
				strBuf.delete(0, strBuf.length());
			}

			info.printVocabulary();
		}
		catch (IOException ex) {
			Print.print("Cannot read file %s", corpus.getName());
		}
	}

    public ArrayList<SentenceInfo> getTokensFromSentences(ArrayList<String>sentences) {
        ArrayList<SentenceInfo> sentenceList = new ArrayList<SentenceInfo>();

        // Get tokens from the sentence.
        for (String sentence : sentences) {
            String[] tokens = sentence.split("\\s+");
            ArrayList<String> stringTokens = new ArrayList<String>();

            for (String token : tokens) {
                if (token.trim().length() != 0) {
                    stringTokens.add(token);
                }
            }

            // If there are tokens then, add the sentence.
            if (stringTokens.size() > 0) {
                SentenceInfo sentenceInfo = new SentenceInfo();
                sentenceInfo.setSentence(sentence);
                sentenceInfo.setTokens(stringTokens);

                sentenceList.add(sentenceInfo);
            }
        }

        return sentenceList;
    }

    public int getTotalNumberOfSentence() {
        return this.sentenceInfoList.size();
    }

    // Private methods.
	////////////////// PARSING
	private void parseText(String text) {
        // Using stanford NLP core library to retrieve tokens.
        ArrayList<String>sentences = getSentencesUsingRegEx(text);
        sentenceInfoList = getTokensFromSentences(sentences);

        // Print sentences for debugging.
        if (debug_mode) {
            for (String sentence : sentences) {
                Print.print(sentence);
            }
        }

        // Tokens are available. Add those to the vocabulary.
        this.info.updateVocabulary(this.sentenceInfoList);

        // Print the vocabulary.
        this.info.printVocabulary();
	}

    ////////////////// GET SENTENCES TOKENS WITH PUNCTUATIONS REMOVED/RETAINED.
	/*private ArrayList<String> getSentencesUsingCoreNLP(String text) {
	    ArrayList<String> sentenceList = new ArrayList<>();

	    Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation(text);

        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        // Get sentences.
        for (CoreMap sentence: sentences) {
            String sentenceStr = sentence.toString();

            // If need to remove punctuations, remove those.
            if (remove_punctuations) {
                sentenceStr = this.removePunctuations(sentenceStr);
            }

            sentenceList.add(sentenceStr);

            if (debug_mode) {
                Print.print(sentenceStr);
            }
        }


        return sentenceList;
    }*/

    private ArrayList<String> getSentencesUsingRegEx(String text) {
        ArrayList<String> sentenceList = new ArrayList<String>();

        String sentenceSplitRegEx = "(?:\\.)\\s+(?=[A-Z])|(?:\\.)(?=\")";
        String[] sentences = text.split(sentenceSplitRegEx);

        // Get sentences.
        for (String sentence: sentences) {
            String sentenceStr = sentence;

            // If need to remove punctuations, remove those.
            if (remove_punctuations) {
                sentenceStr = this.removePunctuations(sentenceStr);
            }

            sentenceList.add(sentenceStr);

            if (debug_mode) {
                Print.print(sentenceStr);
            }
        }


        return sentenceList;
    }


    private String removePunctuations(String sentence) {
	    String cleanString = sentence;
        cleanString = cleanString.replaceAll("(?<=\\b[A-Z])\\.", ""); // replace .'s
        cleanString = cleanString.replaceAll("\\.", " "); // replace .'s
        cleanString = cleanString.replaceAll("(?<=\\b[A-Za-z])\'(?=[a-zA-Z])", ""); // replace .'s
        cleanString = cleanString.replaceAll("-", " "); // Remove - separating two words.
        cleanString = cleanString.replaceAll("\\p{Punct}", ""); // Remove all other punctuations.

        return cleanString;
    }
}