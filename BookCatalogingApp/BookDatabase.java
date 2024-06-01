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
                if (data.length == 8) {
                    String title = data[0];
                    String author = data[1];
                    String genre = data[2];
                    String publishers = data[3];
                    String edition = data[4];
                    int pages = Integer.parseInt(data[5]);
                    String synopsis = data[6];
                    String thumbnailPath = data[7];

                    Book book = new Book(title, author, genre, publishers, edition, pages, synopsis, thumbnailPath);
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

    public Book getBookByTitle (String title) {
        for (Book book: bookDatabase) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
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
            System.out.println(book.getPublisher());
            System.out.println(book.getPages());
            System.out.println(book.getGenre());
            System.out.println(book.getSynopsis());
        } 
    }
}
