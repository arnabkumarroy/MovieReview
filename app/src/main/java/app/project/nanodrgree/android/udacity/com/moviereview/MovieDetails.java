package app.project.nanodrgree.android.udacity.com.moviereview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = MovieDetails.class.getSimpleName();
    final String watchTrailerPath = "http://api.themoviedb.org/3/movie/";
    final String apiKey = "/videos?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e";
    String id = null;
    String trailerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        String imageServerPath = "http://image.tmdb.org/t/p/w500/";
        final String watchTrailerPath = "http://api.themoviedb.org/3/movie/";
        final String apiKey = "/videos?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e";
        Intent newintent = getIntent();
        String newvalue = newintent.getStringExtra("title");
        id = newintent.getStringExtra("id");
        ArrayList<MovieAllDetails> movieDetailsGlobal = (ArrayList<MovieAllDetails>) newintent.getSerializableExtra("MyClass");
        String original_title = null;
        String posterPath = null;
        String releaseDate = null;
        String popularity = null;
        String storyLine = null;
        Log.e(TAG, "newValue: " + newvalue + "id: " + id + "movieDetailsGlobal:" + movieDetailsGlobal.size());

        for (int i = 0; i < movieDetailsGlobal.size(); i++) {
            if (id.equalsIgnoreCase(movieDetailsGlobal.get(i).getId())) {
                original_title = movieDetailsGlobal.get(i).getOriginal_title();
                posterPath = imageServerPath + movieDetailsGlobal.get(i).getPoster_path();
                releaseDate = movieDetailsGlobal.get(i).getRelease_date();
                popularity = movieDetailsGlobal.get(i).getPopularity();
                storyLine = movieDetailsGlobal.get(i).getOverview();
            }
        }
        Log.e(TAG, "releaseDate: " + releaseDate + "posterPath: " + id + "posterPath:" + posterPath);

        //Ttile of the movie
        TextView textViewValue = (TextView) findViewById(R.id.original_title);
        textViewValue.setText(original_title);
        //Poster of the movie
        ImageView imageViewObj = (ImageView) findViewById(R.id.banner);
        Picasso.with(this).load(posterPath).into(imageViewObj);
        //Release Date of the Movie
        TextView release_date = (TextView) findViewById(R.id.release_date);
        release_date.setText(releaseDate);
        //Story Line of the movie
        TextView storyLineDetails = (TextView) findViewById(R.id.plot_synopsis);
        storyLineDetails.setText(storyLine);

        TextView popularityGain = (TextView) findViewById(R.id.popularity);
        popularityGain.setText(popularity);

        Button playTrailor = new Button(this);
        playTrailor.setId(Integer.parseInt(id));
        playTrailor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        playTrailor.setText("Play Trailer");
        playTrailor.setBackground(Drawable.createFromPath("#64FFDA"));
        playTrailor.setTextColor(Integer.parseInt("000000"));
        playTrailor.setTextSize(20);
        LinearLayout layout = (LinearLayout) findViewById(R.id.detailsViewLayout);
        layout.addView(playTrailor);

        Button playTrailorButton = (Button) findViewById(R.id.button);
        playTrailorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetails.this, "Button Clicked",
                        Toast.LENGTH_SHORT).show();
                String finalTrailerUrl = watchTrailerPath + id + apiKey;
                Log.e(TAG, "finalTrailerUrl: " + finalTrailerUrl);
                watchYoutubeVideo(watchTrailerPath + id + apiKey);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Will be use for the YouTube display.
    public void watchYoutubeVideo(String id) {
        GetUpcomingMovieListTask GetUpcomingMovieListTask=new GetUpcomingMovieListTask();
        GetUpcomingMovieListTask.execute();

    }

    private class GetUpcomingMovieListTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... urls) {
            String trailerID = getTrailerfromDB(id);
            Log.e(TAG, "GetUpcomingMovieListTask:" + trailerID);

            return trailerID;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "onPostExecute result:" + result);
            try {
                Log.e(TAG, "trailerID: " + result);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + result));
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + result));
                startActivity(intent);
            }
        }
    }

    //Call the Movie DB API for getting the movie Details.
    protected String getTrailerfromDB(String id)
    {
        // http://api.themoviedb.org/3/movie/upcoming?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e
        URL url = null;
        HttpURLConnection urlConnection = null;
        String upcomingMovieDetails=null;
        BufferedReader reader=null;
        String jsonParseObj=null;

        try {
            url = new URL("http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                Log.e(TAG, "Stream was empty.No value in inputStream");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.e(TAG, "Stream was empty");
                return null;
            }
            upcomingMovieDetails = buffer.toString();
            jsonParseObj=JSONParsingData.getTrailorIDDataFromJson(upcomingMovieDetails);
            Log.e(TAG,"jsonParseObj: "+jsonParseObj);

            if(reader!=null){
                reader.close();
            }

        }
        catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURL" + e);
        }
        catch (IOException e) {
            Log.e(TAG, "IOException"+e);
        }
        catch (JSONException e) {
            Log.e(TAG, "JSONException" + e);
        }
        catch (Exception e) {
            Log.e(TAG, "AnyOther Exception" + e);
        }
        finally {
            urlConnection.disconnect();

        }
        return jsonParseObj;
    }
}
