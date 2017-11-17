import database.Website;
import index.Index;
import ranking.Score;
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
        Map<Website, Double> finalSitesRanked = new HashMap<>();
        Map<Website, Double> foundSitesRanked = new HashMap<>();
        Score ranking = new TFScore();

        for(String word : words) {
            word = word.toLowerCase();
            List<Website> tempList = index.lookup(word);

            //If a word is not found there's no need to search for the next word, skip the rest and return empty map
            if(tempList.isEmpty()) {
                foundSitesRanked.clear();
                return new HashMap<>();
            }

            //Rank and add all websites found during first loop
            if(foundSitesRanked.isEmpty()) {
                for(Website site : tempList) {
                    double score = ranking.getScore(word, site, index);
                    foundSitesRanked.put(site, score);
                }
            }

            //All consecutive loops
            else {
                for(Website site : tempList) {
                    //If the site that the second word is on, is not already in the map, return empty
                    if(!foundSitesRanked.containsKey(site)) {
                        foundSitesRanked.clear();
                        return new HashMap<>();
                    }

                    //Update the score if the website has next word
                    else {
                        double newScore = foundSitesRanked.get(site) + ranking.getScore(word, site, index);
                        foundSitesRanked.replace(site, newScore);
                    }
                }
            }

            //Adding result from each iteration to final result
            //If final result is empty then add all (first iteration)
            if(finalSitesRanked.isEmpty()) {
                for(Website website : foundSitesRanked.keySet()) {
                    finalSitesRanked.put(website, foundSitesRanked.get(website));
                }
            }

            //If final result already contains websites, only keep those that are the same between iterations
            else {
                finalSitesRanked.entrySet().retainAll(foundSitesRanked.entrySet());
            }
        }

        return finalSitesRanked;
    }
}