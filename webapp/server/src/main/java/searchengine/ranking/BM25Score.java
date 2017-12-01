package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

import java.util.List;

public class BM25Score implements Score {
    private double avdl;

    public BM25Score(Index index){
        avdl = getAverageWordsInDatabase(index);
    }

    @Override
    public double getScore(String word, Website site, Index index) {
        TFScore tf = new TFScore();
        double tfScore = tf.getScore(word, site, index);
        double k= 1.75, b = 0.75;
        int dl = site.getWords().size();
        return (tfScore*(k+1))/(k*(1-b+b*dl/avdl)+tfScore);
    }

    /**
     * Takes the index and calculates the average number of words over all websites in the index (AVDL)
     * @param index
     * @return a double, the average.
     */
    private double getAverageWordsInDatabase(Index index){
        List<Website> websitesInDatabase = index.getWebsites();
        int sizeOfDatabase = index.getNumberOfWebsites();
        int counter = 0;
        for(Website site : websitesInDatabase){
            counter = counter + site.getWords().size();
        }
        return counter/sizeOfDatabase;
    }
}
