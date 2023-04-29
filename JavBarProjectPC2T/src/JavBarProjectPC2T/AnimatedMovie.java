package JavBarProjectPC2T;

import java.util.List;

public class AnimatedMovie extends Movie {
	
	private int recommendedAge;

    public AnimatedMovie(String title, String director, int releaseYear, List<String> actors, int recommendedAge) {
        super(title, director, releaseYear, actors);
        this.recommendedAge = recommendedAge;
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }

    public void setRecommendedAge(int recommendedAge) {
        this.recommendedAge = recommendedAge;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("recommended age: %d\n", recommendedAge);
    }
}
