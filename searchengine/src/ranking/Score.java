package ranking;

import database.Website;
import index.Index;

/**
 * Authors: Group M: Sabina, Lisa, Line and Susan.
 */

public interface Score {

    /**
     * Calculates the score of a website.
     * @param word the queryword
     * @param site A Website to score
     * @param index the website-database
     * @return a double that is the score of the website
     */
    double getScore(String word, Website site, Index index);
}
