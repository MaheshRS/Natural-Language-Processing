package HMM;
public class HMM {

    private static void log (String s) {
        System.out.println(s);
    }

    public static void main (String[] args) {
        if (args.length > 0) {
            HMMWeatherModel weatherModel = new HMMWeatherModel(args[0]);
        }
        else {
            log(String.format("No events observed."));
            log(String.format("USAGE:java HMM.java <Sequence of Observed Events>"));
        }
    }
}

