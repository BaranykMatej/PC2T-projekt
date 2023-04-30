package JavBarProjectPC2T;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		Manager manager = new Manager();
		Scanner scanner = new Scanner(System.in);
        boolean running = true;
		
	    try {
	        manager.load_from_database();
	    } catch (SQLException e) {
	        System.out.println("Error loading from database: " + e.getMessage());
	    }

        while (running) {
            System.out.println("Select an option:");
            System.out.println("1. Add movie");
            System.out.println("2. Edit movie");
            System.out.println("3. Delete movie");
            System.out.println("4. Add rating to movie");
            System.out.println("5. List movies");
            System.out.println("6. Search movie");
            System.out.println("7. List actors that participated in more than one movie");
            System.out.println("8. List movies by actor");
            System.out.println("9. Save movie to a file");
            System.out.println("10. Load movie from a file");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter title: ");
                    String title = scanner.nextLine();

                    System.out.println("Enter director: ");
                    String director = scanner.nextLine();

                    System.out.println("Enter release year: ");
                    int releaseYear = 0;
                    while (true) {
                        String releaseYearString = scanner.nextLine();
                        try {
                            releaseYear = Integer.parseInt(releaseYearString);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Enter only digits for the release year!");
                        }
                    }

                    System.out.println("Enter actors (comma separated): ");
                    String actorsString = scanner.nextLine();
                    List<String> actors = Arrays.asList(actorsString.split(","));

                    System.out.println("Enter recommended viewer age (leave blank if not applicable):");
                    String recommendedAgeString = scanner.nextLine();
                    Integer recommendedAge = null;
                    if (!recommendedAgeString.isEmpty()) {
                        while (true) {                         
                            try {
                            	recommendedAge = Integer.parseInt(recommendedAgeString);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Enter only digits !");
                            }
                            recommendedAgeString = scanner.nextLine();
                        }
                    }

                    if (recommendedAge != null) {
                        manager.add_movie(title, director, releaseYear, actors, recommendedAge);
                    } else {
                        manager.add_movie(title, director, releaseYear, actors);
                    }
                    break;
                case 2:
                    System.out.println("Enter movie title to edit:");
                    String titleToEdit = scanner.nextLine();

                    System.out.println("Enter new title :");
                    String newTitle = scanner.nextLine();

                    System.out.println("Enter new director :");
                    String newDirector = scanner.nextLine();

                    System.out.println("Enter new release year :");
                    String releaseYearString = scanner.nextLine();
                    Integer newReleaseYear = null;
                    if (!releaseYearString.isEmpty()) {                       
                        while (true) {                         
                            try {
                            	newReleaseYear = Integer.parseInt(releaseYearString);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Enter only digits !");
                            }
                            releaseYearString = scanner.nextLine();
                        }
                    }

                    System.out.println("Enter new actors (comma separated):");
                    String newActorsString = scanner.nextLine();
                    List<String> newActors = null;
                    if (!newActorsString.isEmpty()) {
                        newActors = Arrays.asList(newActorsString.split(","));
                    }

                    System.out.println("Enter new recommended viewer age (leave blank if not changing):");
                    String newRecommendedAgeString = scanner.nextLine();
                    Integer newRecommendedAge = null;
                    if (!newRecommendedAgeString.isEmpty()) {                    
                        while (true) {                         
                            try {
                            	newRecommendedAge = Integer.parseInt(newRecommendedAgeString);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Enter only digits !");
                            }
                            newRecommendedAgeString = scanner.nextLine();
                        }
                    }

                    manager.editMovie(titleToEdit, newTitle, newDirector, newReleaseYear, newActors, newRecommendedAge);
                    break;
                case 3:
                	System.out.println("Enter the title of the movie to delete:");
                    String deleteTitle = scanner.nextLine();
                    manager.deleteMovie(deleteTitle);
                    break;
                case 4:
                	System.out.print("Enter the title of the movie to rate: ");
                    String RateTitle = scanner.nextLine();
                    System.out.print("Enter the rating (0-10): ");
                    float rating = 0;
                    while (true) {
                        String floatstring = scanner.nextLine();
                        try {
                        	rating = Float.parseFloat(floatstring);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Enter only digits for the release year!");
                        }
                    }
                    System.out.print("Enter a comment (optional): ");
                    String comment = scanner.nextLine();
                    manager.add_rating(RateTitle, rating, comment);
                    break;
                case 5:
                	manager.list_movies();
                    break;
                case 6:
                	System.out.print("Enter the title of the movie to search: ");
                    String searchTitle = scanner.nextLine();
                    manager.search_movie(searchTitle);
                    break;
                case 7:
                	manager.listing_actors();
                    break;
                case 8:
                	System.out.print("Enter the name of the actor: ");
                    String actorName = scanner.nextLine();
                    manager.list_movies_by_actor(actorName);
                    break;
                case 9:
                	System.out.print("Enter the title of the movie that you want to save: ");
                	String titleFile = scanner.nextLine();
                	System.out.print("Enter the name of the file: ");
                	String nameFile = scanner.nextLine();
                	manager.saveMovieToFile(titleFile, nameFile);
                	break;
                case 10:
                	System.out.print("Enter the name of the file that you want to import: ");
                	String nameFileImport = scanner.nextLine();
                	manager.loadMovieFromFile(nameFileImport);
                	break;
                case 0:
                	running = false;
            }
         
        }
        scanner.close();
	    try {
	        manager.save_to_database();
	        System.out.println("Saved to Database !");
	    } catch (SQLException e) {
	        System.out.println("Error saving to database: " + e.getMessage());
	    }
	}
}
