package main;

import java.util.ArrayList;
import java.util.Collections;

public class ProbabilityMatrix {
    private Vocabulary vocab;

    public ProbabilityMatrix(Vocabulary vocab) {
        this.vocab = vocab;
    }

    public void  analyzeQuestions (ArrayList<String> questions) {
        ArrayList<Vocabulary.SentenceInfo>sentenceInfos = this.vocab.getTokensFromSentences(questions);
        //System.out.println(vocab.info.followers);
        for (Vocabulary.SentenceInfo sentenceInfo : sentenceInfos) {
            // Without Smoothing
            Print.print("=====================================================================================");
            Print.print("********************************* Without Smoothing *********************************");
            Print.print("=====================================================================================");
            Print.print("Sentence: %s\n", sentenceInfo.sentence);

            int[][] bigramCountMatrix = this.bigramMatrixForSentence(sentenceInfo, false);
            double[][] bigramProbabilityMatrix = generateProbabilityMatrix(bigramCountMatrix, sentenceInfo, false);
            printStats(bigramCountMatrix, bigramProbabilityMatrix, sentenceInfo.uniqueTokens, sentenceInfo);

            // With Smoothing
            Print.print("=====================================================================================");
            Print.print("******************************* With Add-One Smoothing ******************************");
            Print.print("=====================================================================================");
            Print.print("Sentence: %s\n", sentenceInfo.sentence);
            bigramCountMatrix = this.bigramMatrixForSentence(sentenceInfo, true);
            bigramProbabilityMatrix = generateProbabilityMatrix(bigramCountMatrix, sentenceInfo, true);
            printStats(bigramCountMatrix, bigramProbabilityMatrix, sentenceInfo.uniqueTokens, sentenceInfo);
        }
    }

    private void printStats(int[][]countMatrix, double[][]probabilityMatrix, ArrayList<String>uniqueTokens, Vocabulary.SentenceInfo sentenceInfo) {
        Print.print("\nP(Sentence) " + this.getProbabilityOfSentence(sentenceInfo, probabilityMatrix) + "\n");
        Print.print("* BI-GRAM COUNT TABLE *");
        printCountMatrix(countMatrix, uniqueTokens);

        Print.print("* BI-GRAM PROBABILITY TABLE *");
        printProbabilityMatrix(probabilityMatrix, uniqueTokens);
    }

    private double getProbabilityOfSentence(Vocabulary.SentenceInfo sentenceInfo, double[][] bigramProbabilityMatrix) {
        ArrayList<String> tokens = new ArrayList<>(sentenceInfo.tokens);
        ArrayList<String> uniqueTokens = new ArrayList<>(sentenceInfo.uniqueTokens);

        double probability = 1.f;
        for (int i = 0; i < tokens.size(); i++) {
            if (i != 0) {
                int rowId = uniqueTokens.indexOf(tokens.get(i));
                int columnId = uniqueTokens.indexOf(tokens.get(i - 1));
                double prValue = bigramProbabilityMatrix[columnId][rowId];

                this.printConditionalProbability(tokens.get(i), tokens.get(i - 1), prValue);
                probability *= prValue;
            }
            else {
                double prValue = (double) vocab.getFrequencyForToken(tokens.get(i))/vocab.info.vocabularySize();
                this.printConditionalProbability(tokens.get(i), null, prValue);
                probability *= prValue;
            }
        }

        return probability;
    }

    /// Probability tables
    private double[][] generateProbabilityMatrix (int[][]matrix, Vocabulary.SentenceInfo sentenceInfo, boolean shouldApplySmoothing) {
        ArrayList<String> uniqueTokens = sentenceInfo.uniqueTokens;

        double [][] bigramProbabilityMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            String token = uniqueTokens.get(i);

            for (int j = 0; j < matrix.length; j++) {
                int tokenFrequency = this.vocab.getFrequencyForToken(token);
                    tokenFrequency = shouldApplySmoothing ? (tokenFrequency + this.vocab.info.vocabularySize()) : tokenFrequency;
                    bigramProbabilityMatrix[i][j] = (double) matrix[i][j] / tokenFrequency;
            }
        }

        return bigramProbabilityMatrix;
    }

    /// Frequency tables
    private int[][] bigramMatrixForSentence(Vocabulary.SentenceInfo sentenceInfo, boolean shouldApplySmoothing) {
        ArrayList<String> tokens = new ArrayList<>(sentenceInfo.tokens);
        ArrayList<String> uniqueTokens = new ArrayList<>(sentenceInfo.uniqueTokens);

        // Matrices.
        int[][] bigramCountMatrix = new int[uniqueTokens.size()][uniqueTokens.size()];

        // Initial column.
        for (int i = 0; i < tokens.size(); i++) {
            String prevToken = tokens.get(i);

            for (int j = 0; j < tokens.size(); j++) {
                String currentToken = tokens.get(j);

                int rowIndex = uniqueTokens.indexOf(prevToken);
                int columnIndex = uniqueTokens.indexOf(currentToken);
                int frequency = 0;

                if (vocab.info.followers.containsKey(currentToken)) {
                    ArrayList<String> tokenFollowers = vocab.info.followers.get(currentToken);
                    frequency = (tokenFollowers != null) ? Collections.frequency(tokenFollowers, prevToken) : 0;
                }

                frequency = shouldApplySmoothing ? (frequency + 1) : frequency;
                bigramCountMatrix[rowIndex][columnIndex] = frequency;
            }
        }

        return bigramCountMatrix;
    }

    private void printCountMatrix(int[][]matrix, ArrayList<String> values) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-12s", "Row/Column"));
        for (String value : values) {
            builder.append(String.format("%-12s", value));
        }

        builder.append("\n\n");
        for (int i = 0; i<matrix.length; i++) {
            builder.append(String.format("%-12s", values.get(i)));
            for (int j = 0; j < matrix.length; j++) {
                builder.append(String.format("%-12s", String.valueOf(matrix[i][j])));
            }

            builder.append("\n");
        }


        System.out.println(builder);
    }

    private void printProbabilityMatrix(double[][]matrix, ArrayList<String> values) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-12s", "Row/Column"));
        for (String value : values) {
            builder.append(String.format("%-12s", value));
        }

        builder.append("\n\n");
        for (int i = 0; i<matrix.length; i++) {
            builder.append(String.format("%-12s", values.get(i)));
            for (int j = 0; j < matrix.length; j++) {
                builder.append(String.format("%-12s", String.format("%.5f", matrix[i][j])));
            }

            builder.append("\n");
        }


        System.out.println(builder);
    }

    private void printConditionalProbability(String A, String givenB, double probability) {
        if (givenB == null) {
            System.out.println("Pr(" + A + ") = " + probability);
        }
        else {
            System.out.println("P(" + A + "|" + givenB + ") = " + probability);
        }
    }
}