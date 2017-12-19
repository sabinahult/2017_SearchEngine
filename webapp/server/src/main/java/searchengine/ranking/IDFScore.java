package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

public class IDFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {

        //a word occurs on n websites
        double websitesWithWord = index.lookup(word).size();

        //if the word isn't on any websites, there's no need to go on with the calculations
        if(websitesWithWord == 0) {
            return 0;}

        //The number of websites in the database
        double sizeOfDatabase = index.getNumberOfWebsites();

        //Number of websites in the database divided by number of websites with the word present
        double calculation = sizeOfDatabase/websitesWithWord;

        return logBase2(calculation);
    }

    private double logBase2 (double calculation) {
        return Math.log(calculation)/Math.log(2);
    }
}