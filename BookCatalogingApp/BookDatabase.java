import java.util.*;
import java.io.*;

public class BookDatabase {
    private List<Book> bookDatabase;

    public BookDatabase () {
        bookDatabase = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("bookDataBase.txt"))) {
            String line;
            while ((line = br.readLine()) !=null) {
                String [] data = line.split(";");
                if (data.length == 7) {
                    String title = data[0];
                    String author = data[1];
                    Genre genre = Genre.FICTION;
                    String publishers = data[3];
                    String edition = data[4];
                    int pages = Integer.parseInt(data[5]);
                    String synopsis = data[6];

                    Book book = new Book(title, author, genre, publishers, edition, pages, synopsis);
                    bookDatabase.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBookDatabase () {
        return bookDatabase;
    }

    public void addBookToTheDataBase (Book book) {
        bookDatabase.add(book);
    }

    public void removeBookFromDataBase (Book book) {
       bookDatabase.remove(book);
    }

    public void printBooksInTheDataBase () {
        for (Book book: bookDatabase) {
            System.out.println(book.getTitle());
            System.out.println(book.getAuthor());
            System.out.println(book.getPublishers());
            System.out.println(book.getPages());
            System.out.println(book.getGenre());
            System.out.println(book.getSynopsis());
        } 
    }
}
