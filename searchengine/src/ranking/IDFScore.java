package ranking;

import database.FileHelper;
import database.Website;
import index.Index;
import index.InvertedIndex;

import java.io.File;
import java.util.HashMap;

public class IDFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {
        //a word occurs on n websites
        double numberOfWebsites = index.lookup(word).size();

        //The whole database of websites
        double sizeOfDatabase = index.getNumberOfWebsites();
        double calculation = sizeOfDatabase/numberOfWebsites;
        return logBase2(calculation);
    }

    private double logBase2 (double x){
        return Math.log(x)/Math.log(2);
    }
}
