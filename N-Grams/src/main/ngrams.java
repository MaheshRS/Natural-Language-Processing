package main;

import java.io.*;
import java.util.ArrayList;

public class ngrams {
    public static void main(String[] args) {
        Vocabulary vocab = null;
        if(args.length > 2) {
            vocab = readFiles(args[0]);
        }
        else if(args.length > 1) {
            vocab = readFiles(args[0]);
        }
        else {
            Print.print("Missing Input test sentences.");
            Print.print("USAGE: javac -cp $ClassPath main.ngrams <Path to corpus.txt> <Setence one: in quotes> <Sentence two: in quotes>");
        }

        if (vocab != null) {
            ProbabilityMatrix matrix = new ProbabilityMatrix(vocab);
            if (args.length > 2) {
                ArrayList<String> questions = new ArrayList<>();
                questions.add(args[1]);
                questions.add(args[2]);

                matrix.analyzeQuestions(questions);
            }
            else if (args.length > 1) {
                ArrayList<String> questions = new ArrayList<>();
                questions.add(args[1]);

                matrix.analyzeQuestions(questions);
            }
        }
    }

    private static Vocabulary readFiles(String corpusFilePath) {
        File corpus = new File(corpusFilePath);
        if (corpus.isFile()) {
            // Corpus is available.
            Vocabulary vocab = new Vocabulary(corpus, corpusFilePath);
            vocab.createVocabulary();
            return vocab;
        }
        else {
            // Corpus is not available
            Print.print("%s is not a file", corpusFilePath);
            return null;
        }
    }
}
