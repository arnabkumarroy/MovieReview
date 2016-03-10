package app.project.nanodrgree.android.udacity.com.moviereview;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ARNAB on 3/5/2016.
 */
public class JSONParsingData {

    private static final String LOG_TAG = JSONParsingData.class.getSimpleName();

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    public static ArrayList<MovieAllDetails> getWeatherDataFromJson(String movieDataJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MOVIE_LIST_RESULT = "results";
        final String MOVIE_LIST_OVERVIEW = "overview";
        final String MOVIE_LIST_RELEASE_DATE = "release_date";
        final String MOVIE_LIST_POSTER_PATH = "poster_path";
        final String MOVIE_LIST_ORIGINAL_TITLE = "original_title";
        final String MOVIE_LIST_POPULARITY = "popularity";
        final String MOVIE_LIST_ID = "id";

        JSONObject movieJson = new JSONObject(movieDataJsonStr);
        JSONArray movieJSONArray = movieJson.getJSONArray(MOVIE_LIST_RESULT);


        ArrayList<MovieAllDetails> movieMapDetails= new ArrayList<MovieAllDetails>();
        for(int i = 0; i < movieJSONArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String overview;
            String release_date;
            String poster_path;
            String original_title;
            String popularity;
            String id;
            MovieAllDetails movieItemdetailsObj=new MovieAllDetails();
            // Get the JSON object representing the day
            JSONObject movieDetailsObj = movieJSONArray.getJSONObject(i);
            id = movieDetailsObj.getString(MOVIE_LIST_ID);
            original_title = movieDetailsObj.getString(MOVIE_LIST_ORIGINAL_TITLE);
            poster_path = movieDetailsObj.getString(MOVIE_LIST_POSTER_PATH);
            release_date = movieDetailsObj.getString(MOVIE_LIST_RELEASE_DATE);
            overview = movieDetailsObj.getString(MOVIE_LIST_OVERVIEW);
            popularity = movieDetailsObj.getString(MOVIE_LIST_POPULARITY);
            Log.e(LOG_TAG, "ItemDetails: " + id + ":" + original_title + ":" + poster_path + ":" + release_date + ":" + overview + ":" + popularity);
            //Adding Each movie details in the arrayList
            movieItemdetailsObj.setId(id);
            movieItemdetailsObj.setOriginal_title(original_title);
            movieItemdetailsObj.setOverview(overview);
            movieItemdetailsObj.setPopularity(popularity);
            movieItemdetailsObj.setRelease_date(release_date);
            movieItemdetailsObj.setPoster_path(poster_path);

            //Adding Each moview in the hashmap for retrive later
            movieMapDetails.add(movieItemdetailsObj);

        }


        return movieMapDetails;

    }



    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    public static String getTrailorIDDataFromJson(String movieDataJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MOVIE_LIST_RESULT = "results";
        final String MOVIE_LIST_OVERVIEW = "overview";
        final String MOVIE_LIST_RELEASE_DATE = "release_date";
        final String MOVIE_LIST_POSTER_PATH = "poster_path";
        final String MOVIE_LIST_ORIGINAL_TITLE = "original_title";
        final String MOVIE_LIST_POPULARITY = "popularity";
        final String MOVIE_LIST_KEY = "key";

        JSONObject movieJson = new JSONObject(movieDataJsonStr);
        JSONArray movieJSONArray = movieJson.getJSONArray(MOVIE_LIST_RESULT);
        String movieTrailorid=null;

        for(int i = 0; i < movieJSONArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"

            // Get the JSON object representing the day
            JSONObject movieDetailsObj = movieJSONArray.getJSONObject(i);
            movieTrailorid = movieDetailsObj.getString(MOVIE_LIST_KEY);
            Log.e(LOG_TAG, "movieTrailorid: " + movieTrailorid);

        }


        return movieTrailorid;

    }


}
