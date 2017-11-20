package ranking;

import database.Website;
import index.Index;

import java.util.List;

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
