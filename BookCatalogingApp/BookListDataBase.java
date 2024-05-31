import java.util.*;
import java.io.*;

public class BookListDataBase {
    public List<String> getSpecificBookListOfUser (String filePath, String username) {
        List<String> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) !=null) {
                String[] data = line.split(";");
                if (data.length == 2 && data[0].equals(username)) {
                    String[] bookTitles = data[1].split(",");
                    for (String title: bookTitles) {
                        books.add(title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void saveBookToSpecificList (String filePath, String username, String bookTitle) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            writer.println(username + ";" + bookTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
