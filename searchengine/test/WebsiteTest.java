
import database.Website;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WebsiteTest {
    private static Website site1;
    private static Website site2;
    private static Website site3;
    private static Website site4;

    @BeforeAll
    static void setUp(){
        site1 = new Website("https://anothertitle.com", "Another title", Arrays.asList("adam", "zebra", "snake"));
        site2 = new Website("", "Empty URL", Arrays.asList("one", "word"));
        site3 = new Website("https://al-bank.dk", "", Arrays.asList("banking", "denmark", "bank", "currency"));
        site4 = new Website("https://callme.dk", "Phonecompany", Arrays.asList());
    }

    @Test
    void getTitle() {
        assertEquals("Another title", site1.getTitle());
        assertEquals("", site3.getTitle());
    }

    @Test
    void getUrl() {
        assertEquals("https://al-bank.dk", site3.getUrl());
        assertEquals("", site2.getUrl());
    }

    @Test
    void getWords() {
        assertEquals(4, site3.getWords().size());
        assertEquals(0, site4.getWords().size());
    }

    @Test
    void containsWord() {
        assertTrue(site3.getWords().get(0).contains("banking"));
        assertFalse(site3.getWords().get(3).contains("denmark"));
    }
}