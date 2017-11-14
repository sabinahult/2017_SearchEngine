package ranking;

import database.Website;
import index.Index;

import java.util.List;

public class TDIDFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {
        double tf = TfScore(word, site);
        double idf = IdfScore(word, index);

        return tf*idf;
    }

    private double TfScore(String word, Website site){
        List<String> wordsOnWebpage = site.getWords();
        double occurrenceOfWords = 0;

        for (String s : wordsOnWebpage){
            if(s.equals(word)){
                occurrenceOfWords++;
            }
        }
        return occurrenceOfWords;
    }

    private double IdfScore (String word, Index index){
        //a word occurs on n websites
        double numberOfWebsites = index.lookup(word).size();

        //The whole database of wehsites
        double sizeOfDatabase = index.sizeOfIndex();

        double calculation = sizeOfDatabase/numberOfWebsites;

        return logBase2(calculation);
    }

    private double logBase2 (double x){
        return Math.log(x)/Math.log(2);
    }
}
