package app.project.nanodrgree.android.udacity.com.moviereview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    GridView gridView;
    ArrayList<MovieAllDetails> movieDetailsGlobal=new ArrayList<MovieAllDetails>();
    GridViewActivity  gridAdapterObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateAppdata();
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
               /* Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();*/
                MovieAllDetails movieItem = (MovieAllDetails) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(getApplicationContext(), MovieDetails.class);
                intent.putExtra("title", movieItem.getOriginal_title());
                intent.putExtra("MyClass", movieDetailsGlobal);
                intent.putExtra("id", movieItem.getId());
                //intent.putExtra("image", item.getImage());
                Log.e(TAG, "title: " + movieItem.getOriginal_title() + "image: " + movieItem.getId()+"movieDetailsGlobal:"+movieDetailsGlobal.size());

                //Start details activity
                startActivity(intent);

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
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class GetUpcomingMovieListTask extends AsyncTask<Void, Void, ArrayList<MovieAllDetails>> {
        @Override
        protected ArrayList<MovieAllDetails> doInBackground(Void... urls) {
            ArrayList<MovieAllDetails> movieDetails=getMoviedetailsfromDB();
           Log.e(TAG,"GetUpcomingMovieListTask:"+movieDetails);
           movieDetailsGlobal=movieDetails;
            Log.e(TAG, "movieDetailsGlobal:" + movieDetailsGlobal);
            return movieDetailsGlobal;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<MovieAllDetails> result) {
            Log.e(TAG, "onPostExecute result:" + result);
            movieDetailsGlobal=result;
            Log.e(TAG, "After GridView updated the data onStart:" + movieDetailsGlobal);
            gridAdapterObj = new GridViewActivity(getApplicationContext(),movieDetailsGlobal);
            gridView.setAdapter(gridAdapterObj);
            Log.e(TAG, "onPostExecute result");
        }
    }


    //Call the Movie DB API for getting the movie Details.
    protected ArrayList<MovieAllDetails> getMoviedetailsfromDB()
    {
        // http://api.themoviedb.org/3/movie/upcoming?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e
        URL url = null;
        HttpURLConnection urlConnection = null;
        String upcomingMovieDetails=null;
        BufferedReader reader=null;
        ArrayList<MovieAllDetails>jsonParseObj=null;

        try {
            url = new URL("http://api.themoviedb.org/3/movie/upcoming?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e");
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
            jsonParseObj=JSONParsingData.getWeatherDataFromJson(upcomingMovieDetails);
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


    private void updateAppdata() {
        GetUpcomingMovieListTask getUpcomingMovieListTaskObj=new GetUpcomingMovieListTask();
        getUpcomingMovieListTaskObj.execute();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
