package searchengine;

import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.ranking.*;

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
     * Takes a string of words (full query), and splits them around OR into sub-queries, then passes that as an
     * array to evaluateFullQuery and returns the result of that method.
     * @param fullQuery search words passed by the SearchEngine
     * return A Map of relevant websites(key) and ranking(value)
     */
    public Map<Website, Double> getMatchingWebsites(String fullQuery) {
        String[] subQuery = fullQuery.split("\\b\\s*OR\\s*\\b");
        Map<Website, Double> finalRankedResult = evaluateFullQuery(subQuery);

        return finalRankedResult;
    }


    /**
     * For each of the sub-queries it splits them around a white space into an array of single words.
     * When all words in a sub-query has been looked up, it finally adds all the found websites to final result.
     * @param subQuery Array of strings passed by getMatchingWebsites
     * @return a Map<Website, Ranking> to getMatchingWebsites
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
                //Compare values, and keep site with highest score
                else if (finalResult.get(site) < foundSites.get(site)) {
                    finalResult.put(site, foundSites.get(site));
                }
        }
        return finalResult;
    }

    /**
     * Takes an Array of strings and for each string calls the lookup method of the index, which returns a list of
     * websites that contains the word. If no matching websites an empty list is returned.
     *
     * If the word is found and the foundSites variable is empty, it adds all sites to foundSites. If foundSites already
     * has elements (from a previous iteration) it keeps only those elements that are the same between the two collections,
     * because we only want to return websites that contains all words in the given sub-query.
     * @param words Array of words received from evaluateFullQuery
     * @return a Map<Website, Ranking> that includes all the matching websites on one query
     */
    private Map<Website, Double> evaluateSubQuery(String[] words) {
        Map<Website, Double> finalSitesRanked = new HashMap<>();
        Score ranking = new BM25Score(index);

        for(String word : words) {
            word = word.toLowerCase();
            List<Website> foundSitesList = index.lookup(word);

            //If a word is not found there's no need to search for the next word, skip the rest and return empty map
            if(foundSitesList.isEmpty()) {
                return new HashMap<>();
            }

            //Rank found sites and add it to map (we are scoring sites we might not return though...)
            Map<Website, Double> foundSitesRanked = rankFoundSites(foundSitesList, ranking, word);

            //For the first word, put the websites in the final map
            if(finalSitesRanked.isEmpty()) {
                for(Website siteKey : foundSitesRanked.keySet()) {
                    finalSitesRanked.put(siteKey, foundSitesRanked.get(siteKey));
                }
            } else {
                //If website is already in the final map (because it's not the first iteration), then add up score for that website
                addUpScore(finalSitesRanked, foundSitesList, word, ranking);
            }

            //Making sure we're only returning websites with all words present...
            finalSitesRanked.keySet().retainAll(foundSitesRanked.keySet());
        }

        return finalSitesRanked;
    }

    /**
     * Helper-method created for readability.
     * Calculates the score for a given found website according to search word and adds it along with the score
     * to the map of found sites.
     * @param tempList a list of websites containing the given word
     * @param ranking calculates the score for a website
     * @param word the current word in the query
     * @return a map of sites mapped to a score (key website, value score as a double)
     */
    private Map<Website, Double> rankFoundSites(List<Website> tempList, Score ranking, String word) {
        Map<Website, Double> foundSites = new HashMap<>();

        for(Website site : tempList) {
            double score = ranking.getScore(word, site, index);
            foundSites.put(site, score);
        }
        return foundSites;
    }

    /**
     * Helper-method created for readability.
     * If the found site is already present in final sites (because it's not the first iteration),
     * then calculate score in relation to the query word and add up scores in the final sites map.
     * @param finalSites the final map of sites (key website, value score as a double)
     * @param tempList a list of websites containing the given word
     * @param word the current word in the query
     * @param ranking calculates the score for a website
     */
    private void addUpScore(Map<Website, Double> finalSites, List<Website> tempList, String word, Score ranking) {
        for(Website site : tempList) {
            if(finalSites.containsKey(site)) {
                double newScore = finalSites.get(site) + ranking.getScore(word, site, index);
                finalSites.replace(site, newScore);
            }
        }
    }

    /**
     * Takes a map of website, double and returns a list of websites sorted by score
     * @param rankedSites Websites mapped to a score
     * @return a list of websites sorted by score
     */
    public List<Website> getMatchingWebsitesAsList(Map<Website, Double> rankedSites) {
        //Martins code for turning a Map into a List... :)
        return rankedSites.entrySet().stream().sorted((x,y) ->
                y.getValue(). compareTo(x.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}