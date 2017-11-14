package ranking;

import database.Website;
import index.Index;

import java.util.List;
import java.util.ArrayList;

public class TFScore implements Score {


    @Override
    public int getScore(String word, Website site, Index index) {

        List<String> wordsOnWebpage = site.getWords();
        int occuranceOfWords = 0;

        for (String s : wordsOnWebpage){
            if(s.equals(word)){
                occuranceOfWords++;
            }
        }
        return occuranceOfWords;
    }
}
