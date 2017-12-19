package searchengine;

import searchengine.database.Website;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

class WebsiteTest {
    private static Website site1;
    private static Website site2;

    @BeforeAll
    static void setUp(){
        site1 = new Website("https://anothertitle.com", "Another title", Arrays.asList("adam", "zebra", "snake"));
        site2 = new Website("", "", Arrays.asList());
    }

    @Test
    void getTitle() {
        assertEquals("Another title", site1.getTitle());
        assertEquals("", site2.getTitle());
    }

    @Test
    void getUrl() {
        assertEquals("https://anothertitle.com", site1.getUrl());
        assertEquals("", site2.getUrl());
    }

    @Test
    void getWords() {
        assertEquals(3, site1.getWords().size());
        assertEquals(0, site2.getWords().size());
    }

    @Test
    void containsWord() {
        assertTrue(site1.getWords().contains("adam"));
        assertFalse(site1.getWords().contains("denmark"));
    }
}