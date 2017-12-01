package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

public class IDFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {

        //a word occurs on n websites
        double websitesWithWord = index.lookup(word).size();

        //to avoid errors when dividing by 0. To make it return something useful.
        if(websitesWithWord == 0){ return 0;}

        //The whole database of websites
        double sizeOfDatabase = index.getNumberOfWebsites();
        double calculation = sizeOfDatabase/websitesWithWord;
        return logBase2(calculation);
    }

    private double logBase2 (double calculation){

        return Math.log(calculation)/Math.log(2);
    }
}
