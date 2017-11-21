import database.Website;
import index.Index;
import ranking.IDFScore;
import ranking.Score;
import ranking.TFIDFScore;
import ranking.TFScore;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Group M: Sabina, Lisa, Line and Susan.
 * Overall function of the class is to query the database and return the result to SearchEngine.
 */

public class QueryHandler {
    private Index index;

    public QueryHandler(Index index) {
        this.index = index;
    }

    /**
     * Takes a string of words (full query), and splits them around OR into sub-queries and passes that as an
     * Array to evaluateFullQuery and returns the result of that method.
     * @param fullQuery search words passed by the SearchEngine
     * return A list of relevant websites
     */
    public List<Website> getMatchingWebsites(String fullQuery) {
        String[] subQuery = fullQuery.split("\\b\\s*OR\\s*\\b");
        Map<Website, Double> finalRankedResult = evaluateFullQuery(subQuery);

        //Martins code for turning a Map into a List...
        return finalRankedResult.entrySet().stream().sorted((x,y) -> y.getValue(). compareTo(x.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * For each of the sub-queries it splits them around a white space into an array of single words.
     * When all words in a sub-query has been looked up, it finally adds all the found websites to finalResult.
     * And then it does it all again for the next sub-query, if there is more.
     * @param subQuery Array of strings passed by getMatchingWebsites
     * @return a List<Website> to getMatchingWebsites
     */
    private Map<Website, Double> evaluateFullQuery(String[] subQuery) {
        Map<Website, Double> finalResult = new HashMap<>();

        for (String line : subQuery) {
            String[] words = line.split("\\s+");
            Map<Website, Double> foundSites = evaluateSubQuery(words);

            for (Website site : foundSites.keySet())
                if (!finalResult.containsKey(site)) {
                    finalResult.put(site, foundSites.get(site));
                }

                else {
                    //Compare values, and keep site with highest score
                    if(finalResult.get(site) < foundSites.get(site)) {
                        finalResult.put(site, foundSites.get(site));
                    }
                }
        }
        return finalResult;
    }

    /**
     * Takes an Array of strings and for each string calls the lookup method of the index, which returns a list of
     * websites that contains the word. If no matching websites are found the method breaks.
     *
     * If the word is found and the foundSites variable is empty, it adds all sites to foundSites. If foundSites already
     * has elements (from a previous iteration) it keeps only those elements that are the same between the two collections,
     * because we only want to return websites that contains all words in the given sub-query.
     * @param words Array of words received from evaluateFullQuery
     * @return a List<Website> that includes all the matching websites on one query
     */
    private Map<Website, Double> evaluateSubQuery(String[] words) {
        Map<Website, Double> finalSites = new HashMap<>();
        Score ranking = new TFIDFScore();

        for(String word : words) {
            word = word.toLowerCase();
            List<Website> tempList = index.lookup(word);
            Map<Website, Double> foundSites = new HashMap<>();

            //If a word is not found there's no need to search for the next word, skip the rest and return empty map
            if(tempList.isEmpty()) {
                return new HashMap<>();
            }

            //Rank and add all websites to found sites
            for(Website site : tempList) {
                double score = ranking.getScore(word, site, index);
                foundSites.put(site, score);

                //If website is already in final (because it's not the first iteration), then add up score
                if(finalSites.containsKey(site)) {
                    double newScore = finalSites.get(site) + ranking.getScore(word, site, index);
                    finalSites.replace(site, newScore);
                }
            }

            //Will only happen for the first found word...
            if(finalSites.isEmpty()) {
                for(Website siteKey : foundSites.keySet()) {
                    finalSites.put(siteKey, foundSites.get(siteKey));
                }
            }

            //Making sure we're only returning websites with all words present...
            finalSites.keySet().retainAll(foundSites.keySet());
        }

        return finalSites;
    }
}