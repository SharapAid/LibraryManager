package View.Forms.ModelData;

public class DataModels {
    private final String[] genres = {
            "Fantasy",
            "Science Fiction",
            "Dystopia",
            "Adventure",
            "Mystery",
            "Thriller",
            "Horror",
            "Romance",
            "Historical",
            "Drama",
            "Comedy",
            "Crime",
            "Biography",
            "Autobiography",
            "Memoir",
            "Poetry",
            "Philosophy",
            "Psychology",
            "Education",
            "Religion",
            "Politics",
            "War",
            "Western",
            "Future",
            "Cyberpunk",
            "Steampunk",
            "Post-Apocalyptic",
            "Space Opera",
            "Magical Realism",
            "Young Adult",
            "Children",
            "Classic",
            "Non-fiction",
            "Graphic Novel"
    };

    private final String[] countryCodes = {
            "+39 (Italia)",
            "+375 (Belarus)",
            "+7 (Russia)",
            "+380 (Ukraine)",
            "+1 (USA)"
    };

    public DataModels(){}

    public String[] getGenres() {
        return genres;
    }

    public String[] getCountryCodes() {
        return countryCodes;
    }
}
