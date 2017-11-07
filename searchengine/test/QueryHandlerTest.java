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

class QueryHandlerTest {
    private QueryHandler queryHandlerObject;
    private Index index;

    @BeforeEach
    void setUp() {
        List<Website> sites = new ArrayList<>();
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen", "or")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president", "jimmy", "carter")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("denmark", "president", "chancellor", "germany", "queen", "USA")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("adam", "eve", "snake", "apple")));

        index = new InvertedIndex(new HashMap<>());
        index.build(sites);
        queryHandlerObject = new QueryHandler(index);
    }

    @Test
    void testSingleWord(){
        assertEquals(4, queryHandlerObject.runQuery("queen").size());
        assertEquals(2, queryHandlerObject.runQuery("denmark").size());
        assertEquals(2, queryHandlerObject.runQuery("Denmark").size());
        assertEquals(1, queryHandlerObject.runQuery("JENS").size());
    }

    @Test
    void testMultipleWord(){
        assertEquals(1, queryHandlerObject.runQuery("president jimmy").size());
        assertEquals(2, queryHandlerObject.runQuery("queen denmark").size());
        assertEquals(0, queryHandlerObject.runQuery("queen denmark prince").size());
        assertEquals(0, queryHandlerObject.runQuery("adam KVOR eve").size());
        assertEquals(1, queryHandlerObject.runQuery("or").size());
    }

    @Test
    void testORQueries(){
        assertEquals(4, queryHandlerObject.runQuery("president OR adam").size());
        assertEquals(4, queryHandlerObject.runQuery("queen OR queen").size());
        assertEquals(1, queryHandlerObject.runQuery("hans OR eve").size());
        assertEquals(0, queryHandlerObject.runQuery("OR").size());
        assertEquals(1, queryHandlerObject.runQuery("OR snake OR").size());
        assertEquals(1, queryHandlerObject.runQuery("President jimmy OR ").size());
        assertEquals(1, queryHandlerObject.runQuery("or OR OROR").size());
        assertEquals(1, queryHandlerObject.runQuery("OR adam eve").size());
    }

    @Test
    void testCornerCases(){
        assertEquals(0, queryHandlerObject.runQuery("").size());
        assertEquals(0, queryHandlerObject.runQuery("handsome").size());
        assertEquals(0, queryHandlerObject.runQuery(" ").size());
        assertEquals(0, queryHandlerObject.runQuery("høne").size());
    }
}