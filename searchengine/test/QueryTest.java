import database.Website;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import performance.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class QueryTest {
    @BeforeEach
    void setUp() {
        Query queryObject = new Query();

        String query1 = " "; //empty search string
        String query2 = "handsome"; //word that is not there
        String query3 = "adam";
        String query4 = "queen denmark";
        String query5 = "president jens OR snake";
        String query6 = "denmark queen OR president usa OR chancellor germany";
        String query7 = "adam eve snake apple bible";//contains five consecutive words
        String query8 = "";//empty
        String query9 = "president jimmy carter OR  adam eve bible";//contains three words and three words
        String query10 = "quen denmark";//contains two words - one of them spelled wrong to make an empty list

        List<Website> sites = new ArrayList<>();
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president", "jimmy", "carter")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("denmark", "president", "chancellor", "germany", "queen", "USA")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("adam", "eve", "snake", "apple")));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test(){
        //assertEquals(0, queryObject.makeQuery(query1).size());
        //assertEquals(0, queryObject.makeQuery(query2).size());
        //assertEquals(1, queryObject.makeQuery(query3).size());
        //assertEquals(2, queryObject.makeQuery(query4).size());
        //assertEquals(2, queryObject.makeQuery(query5).size());
        //assertEquals(1, queryObject.makeQuery(query6).size());
        //assertEquals(0, queryObject.makeQuery(query7).size());
        //assertEquals(0, queryObject.makeQuery(query8).size());
        //assertEquals(1, queryObject.makeQuery(query9).size());
        //assertEquals(0, queryObject.makeQuery(query10).size());
        //assertTrue("example4.com", queryObject.makeQuery(query4).getUrl();
        //assertFalse("example3.com", queryObject.makeQuery(query5).getUrl();
    }


}