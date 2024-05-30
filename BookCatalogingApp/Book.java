public class Book {
    private String title;
    private String author;
    private Genre genre;
    private String publishers;
    private String edition;
    private int pages;
    private String synopsis;

    public Book (String title, String author, Genre genre, String publishers, String edition, int pages, String synopsis) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishers = publishers;
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

    public String getPublishers () {
        return publishers;
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