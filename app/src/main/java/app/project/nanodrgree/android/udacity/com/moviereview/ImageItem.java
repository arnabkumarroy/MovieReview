package app.project.nanodrgree.android.udacity.com.moviereview;

/**
 * Created by ARNAB on 3/2/2016.
 */
public class ImageItem {

    private String overview;
    private String release_date;
    private String poster_path;
    private String original_title;
    private String trailer;
    private String popularity;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public ImageItem(String poster_path, String title) {
        super();
        this.poster_path = poster_path;
        this.original_title = title;
        this.id = id;
        this.release_date = release_date;
        this.popularity = popularity;
        this.overview = overview;
        this.trailer = trailer;
    }


    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }



}
