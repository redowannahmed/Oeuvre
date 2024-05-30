public class Book {
    private String title;
    private String author;
    private Genre genre;
    private String publisher;
    private String edition;
    private int pages;
    private String synopsis;

    public Book (String title, String author, Genre genre, String publisher, String edition, int pages, String synopsis) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.edition = edition;
        this.pages = pages;
        this.synopsis = synopsis;
    }

    public String getTitle () {
        return title;
    }

    public String getAuthor () {
        return author;
    }

    public Genre getGenre () {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getEdition () {
        return edition;
    }

    public int getPages () {
        return pages;
    }

    public String getSynopsis () {
        return synopsis;
    }
}