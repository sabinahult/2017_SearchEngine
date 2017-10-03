import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

public class FileHelper {
    public static List<Website> parseFile(String filename) {
        List<Website> sites = new ArrayList<>();
        String url = null;
        String title = null;
        List<String> listOfWords = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(filename), "UTF-8");

            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.startsWith("*PAGE:")) {

                    //If all variables already has a value(because this is not the first iteration,
                    //then create website from data gathered in previous iteration
                    if (url != null && title != null && !listOfWords.isEmpty()) {
                        sites.add(new Website(url, title, listOfWords));
                    }

                    //New website starts
                    url = line.substring(6);
                    title = null;
                    listOfWords.clear();

                } else if (title == null) {
                    title = line;

                } else {
                    listOfWords.add(line);
                }
            }

            if (url != null) {
                sites.add(new Website(url, title, listOfWords));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the given file");
           e.printStackTrace();
        }

        return sites;
    }
}
