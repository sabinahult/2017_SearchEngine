import database.Website;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {
    @BeforeEach
    void setUp() {
        Query queryObject = new Query();

        String query1 = "Queen Denmark";
        String query2 = "Queen Denmark OR President USA";
        String query3 = "Queen Denmark OR President USA OR Chancellor Germany";

        List<Website> sites = new ArrayList<>();
        sites.add(new Website("example1.com", "Example 1", Arrays.asList("queen")));
        sites.add(new Website("example2.com", "Example 2", Arrays.asList("queen", "denmark")));
        sites.add(new Website("example3.com", "Example 3", Arrays.asList("president")));
        sites.add(new Website("example4.com", "Example 4", Arrays.asList("president", "usa")));
        sites.add(new Website("example5.com", "Example 5", Arrays.asList("queen", "president", "jens")));
        sites.add(new Website("example6.com", "Example 6", Arrays.asList("denmark", "president", "chancellor", "germany", "queen", "USA")));
        sites.add(new Website("example7.com", "Example 7", Arrays.asList("adam", "eve", "snake", "apple")));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test(){
        //Given two words the method needs to return a List of websites that contain both words
        //assertEquals(2, queryObject.makeQuery(query1).size());
        //assertEquals(2, queryObject.makeQuery(query2).size());
        //assertEquals(2, queryObject.makeQuery(query3).size());
    }


}