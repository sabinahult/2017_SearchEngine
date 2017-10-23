import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleIndexTest {

    @BeforeEach
    void setUp(){
        List<Website> goodSites = FileHelper.parseFile("test-resources/test-file.txt");
        List<Website> badSites = FileHelper.parseFile("test-resources/test-file-with-errors.txt");
        Index simple = new SimpleIndex();
    }

    @AfterEach
    void tearDown(){}

    @Test
    void build() {

    }

    @Test
    void lookup() {
    }

}
