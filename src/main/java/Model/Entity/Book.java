package Model.Entity;

public class Book {
    private String title;
    private String author;
    private String genre;
    private int index;
    private boolean isAvailable;

    public Book(){}

    public Book(String title, String author, String genre, boolean isAvailable, int index){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = isAvailable;
        this.index = index;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getIndex(){
        return index;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title + " — " + this.author;
    }
}
