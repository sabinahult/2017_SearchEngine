import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina.
 * A website is the basic  entity of the search engine.
 */

public class Website {
    private String title;
    private String url;
    private List<String> words;

    /**
     * Constructor for a website object.
     * @param url A string formatted as an url starting with https//:
     * @param title The title of the website
     * @param words The list of words contained on the website
     */

    public Website(String url, String title, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = new ArrayList<>(words);
    }

    /**
     * Returns the title of the website
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the url of the website
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the list of words for this website
     * @return listOfWords
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Checks whether a word is present on a website or not.
     * @param word The query word
     * @return True if the word is present within the words-list of the website
     */

    public Boolean containsWord(String word) {
        return words.contains(word);
    }

    @Override
    public String toString() {
        return "Website{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", words=" + words +
                '}';
    }
}
