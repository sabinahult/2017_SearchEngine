package ranking;

import database.Website;
import index.Index;

public class TDIDFScore implements Score {

    @Override
    public double getScore(String word, Website site, Index index) {
        return 0;
    }
}
