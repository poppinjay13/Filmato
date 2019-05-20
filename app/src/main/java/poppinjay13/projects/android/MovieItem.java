package poppinjay13.projects.android;

public class MovieItem {

    private int imageResource;
    private String title;

    public MovieItem(int imageResource, String title) {
        this.title = title;
        this.imageResource = imageResource;
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

}