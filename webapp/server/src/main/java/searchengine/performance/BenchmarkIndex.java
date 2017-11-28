package searchengine.performance;

import searchengine.database.FileHelper;
import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.index.InvertedIndex;
import searchengine.index.SimpleIndex;

import java.util.*;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 * The overall function of the benchmarking class is to benchmark query times with different datasets
 * and different implementations.
 */
public class BenchmarkIndex {
    public static void main(String[] args) {
        //Making a list of each data set so it's easier to switch between them later
        List<Website> sitesTiny = FileHelper.parseFile("../data/enwiki-tiny.txt");
        List<Website> sitesSmall = FileHelper.parseFile("../data/enwiki-small.txt");
        List<Website> sitesMedium = FileHelper.parseFile("../data/enwiki-medium.txt");


        List<String> queryWords = new ArrayList<>();
        queryWords.addAll(Arrays.asList("and", "of", "zebra", "japan", "is", "established",
                "urban", "period", "the", "conflicts", "devastating", "Denmark", "jens", "small"));

        //Choose which implementation of index to benchmark. If choosing index.InvertedIndex, remember to also choose either
        //HashMap or TreeMap
        Index index = new InvertedIndex(new HashMap<>());

        //Choose which data set to use, and pass it as an argument
        index.build(sitesSmall);

        //The makingQuery method returns an int, so we're saving that in a variable for printing out
        int result = makingQueries(1000, queryWords, index);
        System.out.println("Number of websites found during warmup: " + result);

        //Re-implemented the TinyTimer class
        TinyTimer tinyTimer = new TinyTimer();
        tinyTimer.start();
        result = makingQueries(1, queryWords, index);
        tinyTimer.end();

        System.out.println("Running queries on dataset: " + tinyTimer.duration() + " nanoseconds.");
        System.out.println("Websites found: " + result);

    }

    //Made a helper-method as suggested for running the code
    private static int makingQueries(int repetitions, List<String> collection, Index index) {
        int foundWebsites = 0;
        for (int i = 0; i < repetitions; i++) {
            for (String query : collection) {
                foundWebsites += index.lookup(query).size();
            }
        }
        return foundWebsites;
    }
}