package ranking;

import database.Website;
import index.Index;

public class IDFScore implements Score {

    @Override
    public int getScore(String word, Website site, Index index) {
        return 0;
    }
}
