package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

import java.util.List;

/**
 * Authors: Line and Lisa
 */

public class BM25Score implements Score {
    private double averageOnAllSites;

    public BM25Score(Index index){
        averageOnAllSites = getAverageWordsInDatabase(index);
    }

    @Override
    public double getScore(String word, Website site, Index index) {
        //Creates a new IDF score for the calculation
        IDFScore idfScore = new IDFScore();
        double idfCalculation = idfScore.getScore(word, site, index);

        //Calculate the tf*(word, website, database)
        double tfplus = calculateTFPlusScore(word, site, index);

        //To get the BM25 score tf*(w, S, D) * IDF score
        return tfplus*idfCalculation;
    }

    /**
     * Takes the index and calculates the average number of words over all websites in the index (AVDL)
     * @param index the websites database
     * @return a double, the average.
     */
    private double getAverageWordsInDatabase(Index index) {
        List<Website> websitesInDatabase = index.getWebsites();
        int sizeOfDatabase = index.getNumberOfWebsites();
        int counter = 0;

        for(Website site : websitesInDatabase){
            counter = counter + site.getWords().size();
        }

        return counter/sizeOfDatabase;
    }

    /**
     *Calculates tf*(word, website, database) = tf(word, website) * ((k+1)/(k*(1-b+b*DL/AVDL)+tf(word, website)))
     * @param word the query word
     * @param site the current site being scored
     * @param index the website database
     * @return a double, the result.
     */

    private double calculateTFPlusScore (String word, Website site, Index index){
        TFScore tf = new TFScore();
        double tfScore = tf.getScore(word, site, index);

        //k and b are constants in the formula
        double k = 1.75, b = 0.75;

        //How many words are there on the specific site that is being ranked
        int wordsOnWebsite = site.getWords().size();

        //Return the result
        return (tfScore*(k+1))/(k*(1-b+b*wordsOnWebsite/averageOnAllSites)+tfScore);
    }
}
