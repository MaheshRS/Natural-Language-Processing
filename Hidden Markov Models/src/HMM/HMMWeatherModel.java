package HMM;

import java.util.ArrayList;

public class HMMWeatherModel {

    private class Result {
        private String observedEventSequence;
        private double estimatedMLE;
        private String predictedStateSequence;

        public Result (String observedEvent) {
            this.observedEventSequence = observedEvent;
        }

        public  void setMLEAndPredictedSequence (double mle, String predictedStateSequence) {
            this.estimatedMLE = mle;
            this.predictedStateSequence = predictedStateSequence;
        }

        public void printResult () {
            log("");
            log(String.format("Observed event sequence: %s", this.observedEventSequence));
            log(String.format("MLE of the observed sequence: %s", String.format("%e", this.estimatedMLE)));
            log(String.format("Predicted state sequence: %s", this.predictedStateSequence));
        }
    }

    private enum weatherState {
        kWeatherStateHot,
        kWeatherStateCold,
    }

    private enum eventState {
        kEventOneIceCream,
        kEventTwoIceCream,
        kEventThreeIceCream,
    }

    private int startStateRowIndex = 0, hotStateRowIndex = 1, coldStateRowIndex = 2;
    private int hotColumnIndex = 0, coldColumnIndex = 1;
    private double[][] transitionMatrix = new  double[3][2];

    private int oneEmissionMatrixRowIndex = 0, twoEmissionMatrixRowIndex = 1, threeEmissionMatrixRowIndex = 2;
    private int hotColumnEmissionMatrixIndex = 0, coldEmissionMatrixIndex = 1;
    private double[][] emissionMatrix = new  double[3][2];

    Result result;
    ResultSet veterbiDistribution = new ResultSet("Veterbi Table Probability Distribution");
    ArrayList<String> veterbiDistributionColumns = new ArrayList<>();

    private static void log (String s) {
        System.out.println(s);
    }

    // Constructor
    public HMMWeatherModel (String inputEventSequence) {
        // Get the events.
        ArrayList<String> events = observations(inputEventSequence);

        // Initialize the transition and emission matrix.
        result = new Result(inputEventSequence);
        this.initializeTransistionMatrix();
        this.initializeEmissionMatrix();

        // Get the predicted sequence of the hidden states and the MLE of the observed events.
        result = estimateEventSequenceMLE(events, result);
        result.printResult();

        veterbiDistribution.setColumns(veterbiDistributionColumns);
        veterbiDistribution.Display();
    }

    // Parse inputs.
    private ArrayList<String> observations (String observation) {
        ArrayList <String> observations = new ArrayList<String>();
        for (char c: observation.toCharArray()) {
            observations.add(String.valueOf(c));
        }

        return observations;
    }

    // Transition matrix initialization.
    private void initializeTransistionMatrix() {
        // The start state.
        this.transitionMatrix[startStateRowIndex][hotColumnIndex] = 0.8;
        this.transitionMatrix[startStateRowIndex][coldColumnIndex] = 0.2;

        // Hot State.
        this.transitionMatrix[hotStateRowIndex][hotColumnIndex] = 0.7;
        this.transitionMatrix[hotStateRowIndex][coldColumnIndex] = 0.3;

        // Cold State.
        this.transitionMatrix[coldStateRowIndex][hotColumnIndex] = 0.4;
        this.transitionMatrix[coldStateRowIndex][coldColumnIndex] = 0.6;

        Record startStateRecord = Record.CreateRecord();
        startStateRecord.put("From/To", "Start");
        startStateRecord.put("Hot", String.valueOf(this.transitionMatrix[startStateRowIndex][hotColumnIndex]));
        startStateRecord.put("Cold", String.valueOf(this.transitionMatrix[startStateRowIndex][coldColumnIndex]));


        Record hotStateRecord = Record.CreateRecord();
        hotStateRecord.put("From/To", "Hot");
        hotStateRecord.put("Hot", String.valueOf(this.transitionMatrix[hotStateRowIndex][hotColumnIndex]));
        hotStateRecord.put("Cold", String.valueOf(this.transitionMatrix[hotStateRowIndex][coldColumnIndex]));

        Record coldStateRecord = Record.CreateRecord();
        coldStateRecord.put("From/To", "Cold");
        coldStateRecord.put("Hot", String.valueOf(this.transitionMatrix[coldStateRowIndex][hotColumnIndex]));
        coldStateRecord.put("Cold", String.valueOf(this.transitionMatrix[coldStateRowIndex][coldColumnIndex]));

        ResultSet transitionDistribution = new ResultSet("Transition Probability Distribution");
        ArrayList<String> transitionColumns = new ArrayList<>();
        transitionColumns.add("From/To");
        transitionColumns.add("Hot");
        transitionColumns.add("Cold");
        transitionDistribution.setColumns(transitionColumns);

        transitionDistribution.addRecord(startStateRecord);
        transitionDistribution.addRecord(hotStateRecord);
        transitionDistribution.addRecord(coldStateRecord);
        transitionDistribution.Display();
    }

    // Emission Matrix
    private void initializeEmissionMatrix() {
        // P(Ice-cream Count | Hot Weather).
        this.emissionMatrix[oneEmissionMatrixRowIndex][hotColumnEmissionMatrixIndex] = 0.4;
        this.emissionMatrix[twoEmissionMatrixRowIndex][hotColumnEmissionMatrixIndex] = 0.3;
        this.emissionMatrix[threeEmissionMatrixRowIndex][hotColumnEmissionMatrixIndex] = 0.3;

        // P(Ice-cream Count | Cold Weather).
        this.emissionMatrix[oneEmissionMatrixRowIndex][coldEmissionMatrixIndex] = 0.4;
        this.emissionMatrix[twoEmissionMatrixRowIndex][coldEmissionMatrixIndex] = 0.4;
        this.emissionMatrix[threeEmissionMatrixRowIndex][coldEmissionMatrixIndex] = 0.2;

        Record oneStateRecord = Record.CreateRecord();
        oneStateRecord.put("Observation/State", "1");
        oneStateRecord.put("Hot", String.valueOf(this.emissionMatrix[oneEmissionMatrixRowIndex][hotColumnEmissionMatrixIndex]));
        oneStateRecord.put("Cold", String.valueOf(this.emissionMatrix[oneEmissionMatrixRowIndex][coldEmissionMatrixIndex]));


        Record twoStateRecord = Record.CreateRecord();
        twoStateRecord.put("Observation/State", "2");
        twoStateRecord.put("Hot", String.valueOf(this.emissionMatrix[twoEmissionMatrixRowIndex][hotColumnEmissionMatrixIndex]));
        twoStateRecord.put("Cold", String.valueOf(this.emissionMatrix[twoEmissionMatrixRowIndex][coldEmissionMatrixIndex]));

        Record threeStateRecord = Record.CreateRecord();
        threeStateRecord.put("Observation/State", "3");
        threeStateRecord.put("Hot", String.valueOf(this.emissionMatrix[threeEmissionMatrixRowIndex][hotColumnEmissionMatrixIndex]));
        threeStateRecord.put("Cold", String.valueOf(this.emissionMatrix[threeEmissionMatrixRowIndex][coldEmissionMatrixIndex]));

        ResultSet emissionDistribution = new ResultSet("Emission Probability Distribution");
        ArrayList<String> emissionColumns = new ArrayList<>();
        emissionColumns.add("Observation/State");
        emissionColumns.add("Hot");
        emissionColumns.add("Cold");
        emissionDistribution.setColumns(emissionColumns);

        emissionDistribution.addRecord(oneStateRecord);
        emissionDistribution.addRecord(twoStateRecord);
        emissionDistribution.addRecord(threeStateRecord);
        emissionDistribution.Display();
    }

    private Result estimateEventSequenceMLE (ArrayList<String> events, Result result) {
       StringBuilder builder = new StringBuilder();

        double probabilityHotState = 0.;
        double probabilityColdState = 0.;
        int idx = 0;

        // Records for display.
        Record hotStateRecord = Record.CreateRecord();
        hotStateRecord.put("State/Observation", "Hot");

        Record coldStateRecord = Record.CreateRecord();
        coldStateRecord.put("State/Observation", "Cold");

        veterbiDistributionColumns.add("State/Observation");

        for (String s : events) {
            eventState event = getEventStateFromObservedEvent(Integer.valueOf(s));
            int eventIndex  = eventIndex(event);

            if (idx == 0) {
                probabilityHotState = (transitionMatrix[startStateRowIndex][hotColumnIndex] * emissionMatrix[eventIndex(event)][hotColumnIndex]);
                probabilityColdState = (transitionMatrix[startStateRowIndex][coldColumnIndex] * emissionMatrix[eventIndex(event)][coldColumnIndex]);
            }
            else {
                double prevStateHotProbability = probabilityHotState;
                double prevStateColdProbability = probabilityColdState;

                // Weather State Hot.
                double phe = transitionMatrix[hotStateRowIndex][hotColumnIndex] * emissionMatrix[eventIndex][hotColumnIndex];
                double ph = prevStateHotProbability * phe;
                double phce = transitionMatrix[coldStateRowIndex][hotColumnIndex] * emissionMatrix[eventIndex][hotColumnIndex];
                double phc = prevStateColdProbability * phce;

                probabilityHotState = Math.max(ph, phc);

                // Weather State Cold.
                double pce = transitionMatrix[coldStateRowIndex][coldColumnIndex] * emissionMatrix[eventIndex][coldColumnIndex];
                double pc = prevStateColdProbability * pce;
                double pche = transitionMatrix[hotStateRowIndex][coldColumnIndex] * emissionMatrix[eventIndex][coldColumnIndex];
                double pch = prevStateHotProbability * pche;

                probabilityColdState = Math.max(pc, pch);
            }

            // Add the sequence.
            if (probabilityHotState > probabilityColdState) {
                builder.append("H");
            }
            else {
                builder.append("C");
            }

            /// Add the records.
            String columnTitle = String.format("(%s) %s", idx, s);
            veterbiDistributionColumns.add(String.valueOf(columnTitle));
            hotStateRecord.put(String.valueOf(columnTitle), String.format("%e", probabilityHotState));
            coldStateRecord.put(String.valueOf(columnTitle), String.format("%e", probabilityColdState));

            idx ++;
        }

        result.setMLEAndPredictedSequence(Math.max(probabilityHotState, probabilityColdState), builder.toString());

        // Update the result set.
        this.veterbiDistribution.addRecord(hotStateRecord);
        this.veterbiDistribution.addRecord(coldStateRecord);

        return result;
    }

    private double probabilityOfEventGivenState(eventState es, weatherState ws) {
        int eventIndex =  0;
        int stateIndex =  0;

        // Get the event index.
        eventIndex = eventIndex(es);

        // Get the state index.
        stateIndex = stateIndex(ws);

        // Return the probability P(Event | Weather).
        return this.emissionMatrix[eventIndex][stateIndex];
    }

    private int eventIndex (eventState es) {
        int eventIndex = 0;

        if (es == eventState.kEventOneIceCream) {
            eventIndex = 0;
        }
        else if (es == eventState.kEventTwoIceCream) {
            eventIndex = 1;
        }
        else {
            eventIndex = 2;
        }

        return eventIndex;
    }

    private int stateIndex (weatherState ws) {
        int stateIndex = 0;

        if (ws == weatherState.kWeatherStateHot) {
            stateIndex = 0;
        }
        else {
            stateIndex = 1;
        }

        return stateIndex;
    }

    private eventState getEventStateFromObservedEvent(int event) {
        // Get event state from the observation.
        if (event == 1) {
            return eventState.kEventOneIceCream;
        }
        else if (event == 2) {
            return eventState.kEventTwoIceCream;
        }
        else {
            return eventState.kEventThreeIceCream;
        }
    }
}
