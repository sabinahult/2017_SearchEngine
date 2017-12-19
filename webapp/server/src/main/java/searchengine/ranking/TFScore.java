package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

public class TFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {
        List<String> wordsOnWebpage = site.getWords();
        double occurrenceOfWords = 0;

        for (String s : wordsOnWebpage){
            if(s.equals(word)){
                occurrenceOfWords++;
            }
        }
        return occurrenceOfWords;
    }
}
