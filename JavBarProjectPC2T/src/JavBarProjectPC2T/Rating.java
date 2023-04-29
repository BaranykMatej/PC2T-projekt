package JavBarProjectPC2T;

public class Rating {
	
	private float number;
    private String comment;

    public Rating(float number, String comment) {
        this.number = number;
        this.comment = comment;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating [pointRating=" + number + ", comment=" + comment + "]";
    }
}
