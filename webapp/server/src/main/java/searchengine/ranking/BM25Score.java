package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

import java.util.List;

public class BM25Score implements Score {


    @Override
    public double getScore(String word, Website site, Index index) {
        TFScore tf = new TFScore();
        double tfScore = tf.getScore(word, site, index);
        double k= 1.75;
        double b = 0.75;
        int dl = getWordsOnWebsite(site);
        double avdl = getAverageWordsInDatabase(index);
        double bm25score = (tfScore*(k+1))/(k*(1-b+b*dl/avdl)+tfScore);
        return bm25score;
    }


    /**
     * Takes the website and looks up how many words there are on the site. (DL)
     * @param site, a Website.
     * @return the number of words on the website
     */
    public int getWordsOnWebsite(Website site){
        int numberOfWords;
        return numberOfWords = site.getWords().size();
    }

    /**
     * Takes the index and calculates the average number of words over all websites in the index.(AVDL)
     * @param index
     * @return a double, the average.
     */
    public double getAverageWordsInDatabase(Index index){
        List<Website> websitesInDatabase = index.getWebsites();
        int sizeOfDatabase = index.getNumberOfWebsites();
        int counter = 0;
        for(Website site: websitesInDatabase){
            counter = counter + site.getWords().size();
        }
        return counter/sizeOfDatabase;
    }
}
