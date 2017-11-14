package ranking;

import database.Website;
import index.Index;

public interface Score {

    double getScore(String word, Website site, Index index);
}
