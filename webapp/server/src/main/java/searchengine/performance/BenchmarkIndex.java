package searchengine.performance;

import searchengine.database.FileHelper;
import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.index.InvertedIndex;
import searchengine.index.SimpleIndex;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 * The overall function of the benchmarking class is to benchmark query times with different index implementations.
 */
public class BenchmarkIndex {
    public static void main(String[] args) {

        //A little splash of color 03-12-17 SH
        final String PURPLE_UNDERLINED = "\033[4;35m";
        final String ANSI_RESET = "\u001B[0m";

        //Parsing our chosen data set into a list of websites
        List<Website> sitesMedium = FileHelper.parseFile("../../data/enwiki-medium.txt");

        //Query words list with a word for each letter in the alphabet 03-12-17 SH
        List<String> queryWordsAlphabet = new ArrayList<>();
        queryWordsAlphabet.addAll(Arrays.asList("small", "table", "urban", "victory", "weather", "xavier", "years",
                "zebra", "America", "british", "colony", "dance", "even", "fire", "goat", "hat",
                "independent", "Japan", "king", "labour", "master", "nation", "over", "park", "queen", "rabbit"));

        //Rebuilt a bit, so everything will be run at once instead of manually changing and choosing index type and data set
        System.out.println(PURPLE_UNDERLINED + "Benchmarking on SimpleIndex:" + ANSI_RESET);
        benchmark(new SimpleIndex(), queryWordsAlphabet, sitesMedium);

        System.out.println(PURPLE_UNDERLINED + "Benchmarking on InvertedIndex - TreeMap:" + ANSI_RESET);
        benchmark(new InvertedIndex(new TreeMap<>()), queryWordsAlphabet, sitesMedium);

        System.out.println(PURPLE_UNDERLINED + "Benchmarking on InvertedIndex - HashMap:" + ANSI_RESET);
        benchmark(new InvertedIndex(new HashMap<>()), queryWordsAlphabet, sitesMedium);
    }

    /**
     * Runs queries given as a list of words on a given index and records the time it takes to look up words and
     * return a list of relevant websites with the given word.
     * Before doing the actual benchmarking this method runs a warmup of the lookup method with 1000 repetitions.
     * @param index An index to benchmark
     * @param queryWords A list of words to look up in the index
     */
    private static void benchmark(Index index, List<String> queryWords, List<Website> sites) {
        index.build(sites);

        /*WARMUP The makingQuery method returns an int, so we're saving that in a variable for printing out so that Java
        doesn't think this part can be skipped */
        int result = makingQueries(1000, queryWords, index);
        System.out.println("Number of websites found during warmup: " + result);

        //Timing the querying
        TinyTimer tinyTimer = new TinyTimer();
        tinyTimer.start();
        result = makingQueries(1, queryWords, index);
        tinyTimer.end();

        System.out.println("Running queries: " + tinyTimer.duration() + " nanoseconds.");
        System.out.println("Websites found: " + result + "\n");
    }

    /**
     * Runs the lookup method on an index a given number of times.
     * @param repetitions How many times the lookup method should be called in the index
     * @param queryWords List of query words
     * @param index A given index to query
     * @return number of found websites
     */
    private static int makingQueries(int repetitions, List<String> queryWords, Index index) {
        int foundWebsites = 0;
        for (int i = 0; i < repetitions; i++) {
            for (String query : queryWords) {
                foundWebsites += index.lookup(query).size();
            }
        }
        return foundWebsites;
    }
}