/**
 * Authors: Group M: Line, Lisa, Susan and Sabina.
 * This class sole purpose is to time running time on methods, mostly for the Benchmarking class,
 * but also for printing out query time when querying the search engine.
 */

public class TinyTimer {

    private long startTime;
    long endTime;

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
        System.out.println("QueryTime: " + duration() + " nanoseconds (" + duration() / 1000 + " microseconds).");
        System.out.println("Provided by TinyTimer :).");
    }
}