package performance;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina.
 * This class sole purpose is to time running time on methods, mostly for the Benchmarking class,
 * but also for printing out query time when querying the search engine.
 */

public class TinyTimer {
    private long startTime;
    long endTime;

    //A splash of color while we wait for some real GUI :)
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Timestamp in nanoseconds
     */
    public void start() {
        startTime = System.nanoTime();
    }

    /**
     * Timestamp in nanoseconds
     */
    public void end() {
        endTime = System.nanoTime();
    }

    /**
     * Subtracts startTime from endTime
     * @return the difference between startTime and endTime
     */
    public long duration() {
        return (endTime - startTime);
    }

    /**
     * Prints out the duration in nanoseconds and microseconds.
     */
    public void printDuration() {
        System.out.println(ANSI_PURPLE + "QueryTime: " + duration() + " nanoseconds (" + duration() / 1000 + " microseconds).");
        System.out.println("Provided by TinyTimer :)." + ANSI_RESET);
    }
}