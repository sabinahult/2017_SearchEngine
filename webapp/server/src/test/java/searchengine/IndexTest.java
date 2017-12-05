package searchengine;

import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.index.InvertedIndex;
import searchengine.index.SimpleIndex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IndexTest {
    Index simpleIndex = null;
    Index treeIndex = null;
    Index hashIndex = null;

    @BeforeEach
    void setUp(){
        List<Website> sites = new ArrayList<>();
        sites.add(new Website("zebra.com", "example", Arrays.asList("zebra")));
        sites.add(new Website("example.com", "urban housing", Arrays.asList("jens", "man", "urban", "jens")));
        sites.add(new Website("adamzoo.com", "adam is at the zoo", Arrays.asList("adam", "zebra")));

        simpleIndex = new SimpleIndex();
        simpleIndex.build(sites);

        treeIndex = new InvertedIndex(new TreeMap<>());
        treeIndex.build(sites);

        hashIndex = new InvertedIndex(new HashMap<>());
        hashIndex.build(sites);
    }

    @AfterEach
    void tearDown(){
        simpleIndex = null;
        treeIndex = null;
        hashIndex = null;
    }

    @Test
    void buildSimple() {
        assertEquals("SimpleIndex{websites=[Website{Title='example'}, Website{Title='urban housing'}, Website{Title='adam is at the zoo'}]}", simpleIndex.toString());
        //make it fail - then you look at it manually and if it is correct you copy paste it in here.
    }

    @Test
    void buildTree() {
        assertEquals("InvertedIndex[websitesMap={adam=[Website{Title='adam is at the zoo'}], jens=[Website{Title='urban housing'}], man=[Website{Title='urban housing'}], urban=[Website{Title='urban housing'}], zebra=[Website{Title='example'}, Website{Title='adam is at the zoo'}]}]", treeIndex.toString());
        //make it fail - then you look at it manually and if it is correct you copy paste it in here.
    }

    @Test
    void buildHash(){
        assertEquals("InvertedIndex[websitesMap={zebra=[Website{Title='example'}, Website{Title='adam is at the zoo'}], jens=[Website{Title='urban housing'}], adam=[Website{Title='adam is at the zoo'}], urban=[Website{Title='urban housing'}], man=[Website{Title='urban housing'}]}]", hashIndex.toString());
        //make it fail - then you look at it manually and if it is correct you copy paste it in here.
    }

    private void lookup(Index index) {// should be the same for both classes
        assertEquals(1, index.lookup("jens").size());
        assertEquals(2, index.lookup("zebra").size());
        assertEquals(0, index.lookup("word4").size());
    }

    @Test
    void lookupSimple(){
        lookup(simpleIndex);
    }

    @Test
    void lookupTree(){
        lookup(treeIndex);
    }

    @Test
    void lookupHash(){
        lookup(hashIndex);
    }
}

