/**
 * Authors: Group M: Line, Lisa, Susan and Sabina.
 */

public class TinyTimer {
    //This way to time is inspired by a search on Stack Overflow

    private long startTime;
    long endTime;

    public void start() {

        startTime = System.nanoTime();
    }

    public void end() {

        endTime = System.nanoTime();
    }

    public long duration() {

        return (endTime - startTime);
    }

    public void printDuration() {
        System.out.println("QueryTime: " + duration() + " nanoseconds (" + duration() / 1000000 + " milliseconds).");
        System.out.println("Provided by TinyTimer :).");
    }
}