public class Review {
    private double rating;
    private String review;

    public Review (double rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public double getRating () {
        return rating;
    }

    public String getReview () {
        return review;
    }
}
