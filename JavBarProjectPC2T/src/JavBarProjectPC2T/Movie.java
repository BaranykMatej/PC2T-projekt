package JavBarProjectPC2T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Movie {
	
	private String title;
	private String director;
	private int release_year;
	private List<String> actors;
	private List<Rating> ratings;

	public Movie (String title, String director, int releaseYear, List<String> actors) {
        this.title = title;
        this.director = director;
        this.release_year = releaseYear;
        this.actors = actors;
        this.ratings = new ArrayList<>();
    }
	
	public void addRating(float number, String comment) {
        ratings.add(new Rating(number, comment));
    }

    public float getAvgRating() {
        if (ratings.size() == 0) {
            return 0.0f;
        }
        float totalRating = 0.0f;
        for (Rating r : ratings) {
            totalRating += r.getNumber();
        }
        return totalRating / ratings.size();
    }

    public List<Rating> getViewerRatings() {
    	List<Rating> sortedRatings = new ArrayList<>(ratings);
        Collections.sort(sortedRatings, new Comparator<Rating>() {
            public int compare(Rating r1, Rating r2) {
                return Float.compare(r2.getNumber(), r1.getNumber());
            }
        });
        return sortedRatings;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return release_year;
    }
    
    public void setReleaseYear(int release_year) {
        this.release_year = release_year;
    }

    public List<String> getActors() {
        return new ArrayList<>(actors);
    }
    
    public void setActors(List<String> actors) {
    	this.actors = actors;
    }

    @Override
    public String toString() {
    	List<Rating> sortedRatings = getViewerRatings();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s released in (%d), directed by %s, starring %s\n",
                title, release_year, director, String.join(", ", actors)));
        if (sortedRatings.isEmpty()) {
            sb.append("No viewer ratings available\n");
        } else {
            sb.append("Viewer ratings (sorted in descending order based on the point rating):\n");
            for (Rating rating : sortedRatings) {
                sb.append(String.format("%.2f - %s\n", rating.getNumber(), rating.getComment()));
            }
        }
        return sb.toString();
    }

}
