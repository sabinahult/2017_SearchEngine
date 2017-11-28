package searchengine.ranking;

import searchengine.database.Website;
import searchengine.index.Index;

import java.util.List;

public class TFIDFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {
        TFScore tf = new TFScore();
        IDFScore idf = new IDFScore();

        return (tf.getScore(word, site, index))*(idf.getScore(word, site, index));
    }
}
