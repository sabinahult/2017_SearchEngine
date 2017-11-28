package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

/**
 * Authors: Group M: Sabina, Lisa, Line and Susan.
 */

public interface Score {

    /**
     * Calculates the score of a website.
     * @param word the query word
     * @param site A Website to score
     * @param index the website-database
     * @return a double that is the score of the website
     */
    double getScore(String word, Website site, Index index);
}
