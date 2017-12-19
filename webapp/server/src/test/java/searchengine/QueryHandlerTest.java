package searchengine;

import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.index.InvertedIndex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

class QueryHandlerTest {
    private QueryHandler queryHandlerObject;

    @BeforeEach
    void setUp() {
        List<Website> sites = new ArrayList<>();
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen", "or", "queen", "queen")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark", "queen")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president", "jimmy", "carter")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("denmark", "president", "chancellor", "germany", "queen", "USA")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("adam", "eve", "snake", "apple")));

        Index index = new InvertedIndex(new HashMap<>());
        index.build(sites);
        queryHandlerObject = new QueryHandler(index);
    }

    @Test
    void testSingleWord(){
        assertEquals(4, queryHandlerObject.getMatchingWebsites("queen").size());
        assertEquals(2, queryHandlerObject.getMatchingWebsites("denmark").size());
        assertEquals(2, queryHandlerObject.getMatchingWebsites("Denmark").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("JENS").size());
    }

    @Test
    void testMultipleWord(){
        assertEquals(1, queryHandlerObject.getMatchingWebsites("jimmy president").size());
        assertEquals(2, queryHandlerObject.getMatchingWebsites("queen denmark").size());
        assertEquals(0, queryHandlerObject.getMatchingWebsites("queen denmark prince").size());
    }

    @Test
    void testORQueries(){
        assertEquals(4, queryHandlerObject.getMatchingWebsites("president OR adam").size());
        assertEquals(4, queryHandlerObject.getMatchingWebsites("queen OR queen").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("hans OR eve").size());
    }

    @Test
    void testCornerCases(){
        assertEquals(0, queryHandlerObject.getMatchingWebsites("").size());
        assertEquals(0, queryHandlerObject.getMatchingWebsites("handsome").size());
        assertEquals(0, queryHandlerObject.getMatchingWebsites(" ").size());
        assertEquals(0, queryHandlerObject.getMatchingWebsites("høne").size());
        assertEquals(0, queryHandlerObject.getMatchingWebsites("OR").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("OR snake OR").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("President jimmy OR ").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("or OR OROR").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("OR adam eve").size());
        assertEquals(0, queryHandlerObject.getMatchingWebsites("adam KVOR eve").size());
        assertEquals(1, queryHandlerObject.getMatchingWebsites("or").size());
        assertEquals(4, queryHandlerObject.getMatchingWebsites("  president  OR  adam   ").size());
    }

    /*To check that the QueryHandler returns the websites in correct order we create the ranked map and return
    it as a sorted list (as our map doesn't guarantee any specific order). We check that the title of the
    websites are at the index we expect them to be.*/
    @Test
    void testRanking(){
        assertEquals("Example 1", queryHandlerObject.getMatchingWebsitesAsList(queryHandlerObject.getMatchingWebsites("queen")).get(0).getTitle());
        assertEquals("Example 2", queryHandlerObject.getMatchingWebsitesAsList(queryHandlerObject.getMatchingWebsites("queen")).get(1).getTitle());
        assertEquals("Example 4", queryHandlerObject.getMatchingWebsitesAsList(queryHandlerObject.getMatchingWebsites("queen")).get(2).getTitle());
        assertEquals("Example 5", queryHandlerObject.getMatchingWebsitesAsList(queryHandlerObject.getMatchingWebsites("queen")).get(3).getTitle());
    }
}