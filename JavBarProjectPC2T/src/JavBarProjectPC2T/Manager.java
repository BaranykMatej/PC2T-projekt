package JavBarProjectPC2T;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
	private Connection connection;
	private Map<String, Movie> movies;

    public Manager() {
        movies = new HashMap<>();
    }

    public void add_movie(String title, String director, int releaseYear, List<String> actors) {
        Movie movie = new ClassicMovie(title, director, releaseYear, actors);
        movies.put(title, movie);
    }
    
    public void add_movie(String title, String director, int releaseYear, List<String> actors, int recommendedViewerAge) {
        Movie movie = new AnimatedMovie(title, director, releaseYear, actors, recommendedViewerAge);
        movies.put(title, movie);
    }

    public void editMovie(String title, String newTitle, String director, Integer releaseYear, List<String> actors, Integer recommendedAge) {
    	Movie movie = movies.get(title);
        if (movie != null) {
            movie.setTitle(newTitle);
            movie.setDirector(director);
            movie.setReleaseYear(releaseYear);
            movie.setActors(actors);
            if (recommendedAge != null && movie instanceof AnimatedMovie) {
                AnimatedMovie animatedMovie = (AnimatedMovie) movie;
                animatedMovie.setRecommendedAge(recommendedAge);
            }
        } else {
            System.out.println("Movie not found");
        }
    }
    
    public void deleteMovie(String title) {
        movies.remove(title);
    }
    
    public void add_rating(String title, float number, String comment) {
        Movie movie = movies.get(title);
        if (movie != null) {
            movie.addRating(number, comment);
        } else {
            System.out.println("Movie not found");
        }
    }
    
    public void list_movies() {
        System.out.println("Movies:\n");
        for (Movie movie : movies.values()) {
            System.out.println(movie.toString());
        }
    }
    
    public void search_movie(String title) {
        Movie movie = movies.get(title);
        if (movie != null) {
            System.out.println(movie.toString());
        } else {
            System.out.println("Movie not found");
        }
    }
    
    public void listing_actors() {
        Map<String, List<String>> actorMovies = new HashMap<String, List<String>>();
        for (Movie movie : movies.values()) {
            List<String> actors = movie.getActors();
            for (String actor : actors) {
                if (actorMovies.containsKey(actor)) {
                    actorMovies.get(actor).add(movie.getTitle());
                } else {
                    List<String> movieList = new ArrayList<String>();
                    movieList.add(movie.getTitle());
                    actorMovies.put(actor, movieList);
                }
            }
        }

        System.out.println("Actors who have participated in more than one movie:");
        for (Map.Entry<String, List<String>> entry : actorMovies.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println(entry.getKey() + ": " + entry.getValue().toString());
            }
        }
        System.out.println("");
    }
    
    public void list_movies_by_actor(String actor) {
        List<String> moviesByActor = new ArrayList<String>();
        for (Movie movie : movies.values()) {
            if (movie.getActors().contains(actor)) {
                moviesByActor.add(movie.getTitle());
            }
        }

        if (moviesByActor.size() > 0) {
            System.out.println("Movies featuring " + actor + ": " + moviesByActor.toString());
        } else {
            System.out.println("No movies found featuring " + actor);
        }
    }
    
    public void save_to_database() throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/projektpc2t?useSSL=false";
        String user = "root";
        String pswd = "";

        connection = DriverManager.getConnection(url, user, null);
        
        Statement first_statement = connection.createStatement();
        first_statement.executeUpdate("delete from movies");

        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO movies (title, director, release_year, actors, recommended_age) " +
            "VALUES (?, ?, ?, ?, ?)"
        );

        for (Movie movie : movies.values()) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirector());
            statement.setInt(3, movie.getReleaseYear());
            List<String> actors = movie.getActors();
            String actorsString = String.join(",", actors);
            statement.setString(4, actorsString);
            if (movie instanceof AnimatedMovie) {
                AnimatedMovie animatedMovie = (AnimatedMovie) movie;
                statement.setInt(5, animatedMovie.getRecommendedAge());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            statement.executeUpdate();
        }
        connection.close();
    }

    public void load_from_database() throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");

    	
        String url = "jdbc:mysql://localhost:3306/projektpc2t?useSSL=false";
        String user = "root";
        String pswd = "";

        connection = DriverManager.getConnection(url, user, null);

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM movies");

        while (result.next()) {
            String title = result.getString("title");
            String director = result.getString("director");
            int releaseYear = result.getInt("release_year");
            String actorsString = result.getString("actors");
            List<String> actors = Arrays.asList(actorsString.split(","));
            Integer recommendedAge = result.getInt("recommended_age");
            if (result.wasNull()) {
                recommendedAge = null;
            }
            if (recommendedAge != null) {
                movies.put(title, new AnimatedMovie(title, director, releaseYear, actors, recommendedAge));
            } else {
                movies.put(title, new ClassicMovie(title, director, releaseYear, actors));
            }
        }

        connection.close();
    }
    
    public void saveMovieToFile(String title, String filename) throws IOException {
        Movie movie = movies.get(title);
        if (movie != null) {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println(movie.getTitle());
            writer.println(movie.getDirector());
            writer.println(movie.getReleaseYear());
            writer.println(String.join(", ", movie.getActors()));
            if (movie instanceof AnimatedMovie) {
                AnimatedMovie animatedMovie = (AnimatedMovie) movie;
                writer.println(animatedMovie.getRecommendedAge());
            }
            writer.close();
        } else {
            System.out.println("Movie not found");
        }
    }
    
    public void loadMovieFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String title = reader.readLine();
            String director = reader.readLine();
            int releaseYear = Integer.parseInt(reader.readLine());
            List<String> actors = Arrays.asList(reader.readLine().split(", "));
            Integer recommendedAge = null;
            String ageString = reader.readLine();
            if (ageString != null && !ageString.isEmpty()) {
                recommendedAge = Integer.parseInt(ageString);
            }
            if (recommendedAge != null) {
                add_movie(title, director, releaseYear, actors, recommendedAge);
            } else {
                add_movie(title, director, releaseYear, actors);
            }
            System.out.println("Imported !");
        } catch (IOException e) {
            System.out.println("Failed to load movie from file (file might not exist, check your spelling): " + fileName);
            e.printStackTrace();
        }
    }

}
