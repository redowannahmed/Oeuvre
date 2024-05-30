import java.util.*;

public class User {
    private String username;
    private int userID;
    private String password;
    private List<Book> readBooks;
    private List<Book> wishToReadBooks;
    private List<Book> favoriteBooks;

    public User(String username, String password, int userID) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.readBooks = new ArrayList<>();
        this.wishToReadBooks = new ArrayList<>();
        this.favoriteBooks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getUserID () {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public void addToReadBooks (Book book) {
        readBooks.add(book);
    }

    public void addToWishToReadBooks (Book book) {
        wishToReadBooks.add(book);
    }

    public void addToFavoriteBooks (Book book) {
        favoriteBooks.add(book);
    }

    public List<Book> getReadBooks() {
        return readBooks;
    }

    public List<Book> getWishToReadBooks() {
        return wishToReadBooks;
    }

    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }
}
