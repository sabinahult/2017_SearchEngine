import database.Website;
import index.Index;
import index.InvertedIndex;
import index.SimpleIndex;
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

        assertEquals("SimpleIndex{websites=[database.Website{title='example', url='zebra.com', words=[zebra]}, database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}, database.Website{title='adam is at the zoo', url='adamzoo.com', words=[adam, zebra]}]}", simpleIndex.toString());
        //make it fail - then you look at it manually and if it is correct you copy paste it in here.
    }

    @Test
    void buildTree() {
        assertEquals("InvertedIndex{websitesMap={adam=[database.Website{title='adam is at the zoo', url='adamzoo.com', words=[adam, zebra]}], jens=[database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}], man=[database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}], urban=[database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}], zebra=[database.Website{title='example', url='zebra.com', words=[zebra]}, database.Website{title='adam is at the zoo', url='adamzoo.com', words=[adam, zebra]}]}}", treeIndex.toString());
        //make it fail - then you look at it manually and if it is correct you copy paste it in here.
    }

    @Test
    void buildHash(){
        assertEquals("InvertedIndex{websitesMap={zebra=[database.Website{title='example', url='zebra.com', words=[zebra]}, database.Website{title='adam is at the zoo', url='adamzoo.com', words=[adam, zebra]}], jens=[database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}], adam=[database.Website{title='adam is at the zoo', url='adamzoo.com', words=[adam, zebra]}], urban=[database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}], man=[database.Website{title='urban housing', url='example.com', words=[jens, man, urban, jens]}]}}", hashIndex.toString());
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

