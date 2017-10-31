import database.Website;
import index.Index;
import index.InvertedIndex;
import org.junit.jupiter.api.AfterEach;
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
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president", "jimmy", "carter")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("denmark", "president", "chancellor", "germany", "queen", "USA")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("adam", "eve", "snake", "apple")));

        queryHandlerObject = new QueryHandler();
        index = new InvertedIndex(new HashMap<>());
        index.build(sites);
    }

    @AfterEach
    void teardown(){
        List<Website> sites = null;

        index = null;
    }

    @Test
    void testSingleWord(){
        assertEquals(4, queryHandlerObject.runQuery("queen", index).size());
        assertEquals(2, queryHandlerObject.runQuery("denmark", index).size());
        assertEquals("Example 3", queryHandlerObject.runQuery("president", index).get(0).getTitle());
    }

    @Test
    void testMultipleWord(){
        assertEquals(1, queryHandlerObject.runQuery("president jimmy", index).size());
        assertEquals(2, queryHandlerObject.runQuery("queen denmark", index).size());
        assertEquals(0, queryHandlerObject.runQuery("queen denmark prince", index).size());
    }

    @Test
    void testORQueries(){
        assertEquals(4, queryHandlerObject.runQuery("queen or queen", index).size());
        assertEquals(4, queryHandlerObject.runQuery("president or adam", index).size());
        assertEquals(1, queryHandlerObject.runQuery("hans or eve", index).size());
    }

    @Test
    void testCornerCases(){
        assertEquals(0, queryHandlerObject.runQuery("", index).size());
        assertEquals(0, queryHandlerObject.runQuery("handsome", index).size());
        assertEquals(0, queryHandlerObject.runQuery(" ", index).size());
        assertEquals(0, queryHandlerObject.runQuery("høne", index).size());
    }
}