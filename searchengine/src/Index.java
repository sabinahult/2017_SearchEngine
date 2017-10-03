import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

public interface Index {

    void build(List<Website> listOfWebsites);

    List<Website> lookup(String word);
}
