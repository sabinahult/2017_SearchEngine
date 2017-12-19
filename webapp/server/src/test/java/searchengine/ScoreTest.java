package searchengine;

import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.index.InvertedIndex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import searchengine.ranking.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

class ScoreTest {
    private List<Website> sites;
    private Score tfScore;
    private Score idfScore;
    private Score tfidfScore;
    private Index index;
    private Score bm25Score;

    @BeforeEach
    void setUp() {
        sites = new ArrayList<>();
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen", "or")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president", "jimmy", "carter")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("denmark", "president", "chancellor",
                "germany", "queen", "USA")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("adam", "eve", "snake", "apple")));

        //Same word twice (queen, is on five sites in all)
        sites.add(new Website("example7.com", "Example 7", Arrays.asList("queen", "denmark", "nytårstale",
                "skål", "queen")));

        //Same word twice (president, is on 4 sites in all)
        sites.add(new Website("example8.com", "Example 8", Arrays.asList("USA", "president", "Trump", "no",
                "good", "president", "worst", "ever")));

        //Same word three times (germany, is on 2 sites in all)
        sites.add(new Website("example9.com", "Example 9", Arrays.asList("germany", "brandenburger", "tor",
                "chancellor", "germany", "Berlin", "punk", "germany", "is", "awesome")));

        index = new InvertedIndex(new HashMap<>());
        index.build(sites);

        tfScore = new TFScore();
        idfScore = new IDFScore();
        tfidfScore = new TFIDFScore();
        bm25Score = new BM25Score(index);
    }

    @Test
    void getScore() {
        assertEquals(0, tfScore.getScore("foo", sites.get(5), index));
        assertEquals(2, tfScore.getScore("president", sites.get(7), index));
        assertEquals(0.8479969065549501, idfScore.getScore("queen", sites.get(0), index));
        assertEquals(1.6959938131099002, tfidfScore.getScore("queen", sites.get(6), index));
        assertEquals(1.7518660562103074, bm25Score.getScore("germany", sites.get(4), index));
    }

    @Test
    void getScoreCapitalLetters() {
        assertEquals(0, tfScore.getScore("QUEEN", sites.get(1), index));
        assertEquals(0, idfScore.getScore("QUEEN", sites.get(1), index));
        assertEquals(0, tfidfScore.getScore("QUEEN", sites.get(1), index));
        assertEquals(0, bm25Score.getScore("QUEEN", sites.get(1), index));
    }
}