package poppinjay13.projects.android;

import java.util.List;

public class MoviePOJO {

    private int imageResource;
    private String title;
    private String description;
    private int rating;

    public MoviePOJO(int imageResource, String title, String description, int rating) {
        this.title = title;
        this.imageResource = imageResource;
        this.description = description;
        this.rating = rating;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public static class MovieResult {
        private List<MoviePOJO> results;

        public List<MoviePOJO> getResults() {
            return results;
        }
    }
}