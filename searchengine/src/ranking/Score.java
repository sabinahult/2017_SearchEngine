package ranking;

import database.Website;
import index.Index;

public interface Score {

    int getScore(String word, Website site, Index index);
}
