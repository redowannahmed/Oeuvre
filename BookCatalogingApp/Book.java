public class Book {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private String edition;
    private int pages;
    private String synopsis;
    private String thumbnailPath;

    public Book (String title, String author, String genre, String publisher, String edition, int pages, String synopsis, String thumbnailPath) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.edition = edition;
        this.pages = pages;
        this.synopsis = synopsis;
        this.thumbnailPath = thumbnailPath;
    }

    public String getTitle () {
        return title;
    }

    public String getAuthor () {
        return author;
    }

    public String getGenre () {
        return genre;
    }

    public String getPublisher () {
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

    public String getThumbnailPath () {
        return thumbnailPath;
    }
}
