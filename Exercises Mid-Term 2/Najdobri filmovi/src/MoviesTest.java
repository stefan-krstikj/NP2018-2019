import java.util.*;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
class MoviesList{
    ArrayList<Movie> moviesList;

    public MoviesList(){
        moviesList = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings){
        Movie insertMovie = new Movie(title, ratings);
        moviesList.add(insertMovie);
    }

    public List<Movie> top10ByAvgRating(){
        List<Movie> outPutMovies = new ArrayList<>();
        outPutMovies.addAll(moviesList);

        Comparator avgRatingComparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Movie m1 = (Movie) o1;
                Movie m2 = (Movie) o2;

                if(m1.avgRating() == m2.avgRating()){
                    return m1.movieName.compareTo(m2.movieName);
                }
                else{
                    return Float.compare(m2.avgRating(), m1.avgRating());
                }
            }
        };

        outPutMovies.sort(avgRatingComparator);
        outPutMovies = outPutMovies.subList(0, 10);
        return outPutMovies;
    }

    public List<Movie> top10ByRatingCoef(){
        List<Movie> outPutMovies = new ArrayList<>();
        outPutMovies.addAll(moviesList);

        Comparator compareRatingByCoef = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Movie m1 = (Movie) o1;
                Movie m2 = (Movie) o2;

                if(m1.ratingCoef() == m2.ratingCoef()){
                    return m1.movieName.compareTo(m2.movieName);
                }
                else{
                    return Float.compare(m2.ratingCoef(), m1.ratingCoef());
                }
            }
        };

        outPutMovies.sort(compareRatingByCoef);

        outPutMovies = outPutMovies.subList(0, 10);

        return outPutMovies;
    }
}

class Movie{
    String movieName;
    int[] ratings;

    public Movie(String movieName, int[] ratings) {
        this.movieName = movieName;
        this.ratings = ratings;
    }
    float ratingCoef(){
        return (avgRating() * ratings.length);
    }
    float avgRating(){
        float avg = 0;
        for(int i = 0; i < ratings.length; i++)
            avg+=ratings[i];
        avg = avg / ratings.length;
        return avg;
    }

    int maxRatings(){
        int c = 0;
        for(int i = 0; i < ratings.length; i++)
            c+=ratings[i];
        return c;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", movieName, avgRating(), ratings.length);
    }
}