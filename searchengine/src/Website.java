import java.util.ArrayList;
import java.util.List;

public class Website {
    private String title;
    private String url;
    private List<String> words;

    public Website(String url, String title, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = words;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Boolean containsWord(String word) {
        return words.contains(word);

    }
}
