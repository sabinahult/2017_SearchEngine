import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */
public class BenchmarkIndex {
    public static void main(String[] args) {
    List<Website> sites = FileHelper.parseFile("../data/enwiki-medium.txt");
    List<String> queryWords = new ArrayList<>();
    //
    queryWords.add("and");
    queryWords.add("of");
    queryWords.add("copenhagen");
    queryWords.add("japan");
    queryWords.add("is");
    queryWords.add("established");
    queryWords.add("urban");
    queryWords.add("period");
    queryWords.add("the");
    queryWords.add("conflicts");
    queryWords.add("devastating");
    queryWords.add("Denmark"); //capitalized
    queryWords.add("jens"); //is not there

    Index index = new SimpleIndex();
    index.build(sites);

    int localWebsites = 0;

    int warmupRepetitions = 1000;
    for(int i = 0; i < warmupRepetitions; i++){

        for(String query : queryWords){
            localWebsites += index.lookup(query).size();
        }
    }

    int foundWebsites = 0;

    long startTime = System.nanoTime();

    for(String query : queryWords){
        foundWebsites += index.lookup(query).size();
    }

    long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Running queries on dataset " + (elapsedTime/1000) + " microseconds.");

    System.out.println("Websites found: " + foundWebsites);}
}
