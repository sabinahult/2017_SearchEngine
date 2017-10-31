import database.Website;
import index.Index;
import index.InvertedIndex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryTest {
    private Query queryObject;
    private Index index;

    @BeforeEach
    void setUp() {
        List<Website> sites = new ArrayList<>();
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president", "jimmy", "carter")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("denmark", "president", "chancellor", "germany", "queen", "USA")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("adam", "eve", "snake", "apple")));

        queryObject = new Query();
        index = new InvertedIndex(new HashMap<>());
        index.build(sites);
    }

    @Test
    void testSingleWord(){
        assertEquals(4, queryObject.runQuery("queen", index).size());
        assertEquals(2, queryObject.runQuery("denmark", index).size());
        assertEquals("Example 3", queryObject.runQuery("president", index).get(0).getTitle());
    }

    @Test
    void testMultipleWord(){
        assertEquals(1, queryObject.runQuery("president jimmy", index).size());
        assertEquals(2, queryObject.runQuery("queen denmark", index).size());
        assertEquals(0, queryObject.runQuery("queen denmark prince", index).size());
    }

    @Test
    void testORQueries(){
        assertEquals(4, queryObject.runQuery("queen or queen", index).size());
        assertEquals(4, queryObject.runQuery("president or adam", index).size());
        assertEquals(1, queryObject.runQuery("hans or eve", index).size());
    }

    @Test
    void testCornerCases(){
        assertEquals(0, queryObject.runQuery("", index).size());
        assertEquals(0, queryObject.runQuery("handsome", index).size());
        assertEquals(0, queryObject.runQuery(" ", index).size());
        assertEquals(0, queryObject.runQuery("høne", index).size());
    }
}