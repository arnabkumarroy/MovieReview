package app.project.nanodrgree.android.udacity.com.moviereview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = MovieDetails.class.getSimpleName();
    final String watchTrailerPath="http://api.themoviedb.org/3/";
    final String apiKey="videos?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e";
    String id=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        String imageServerPath="http://image.tmdb.org/t/p/w500/";
        final String watchTrailerPath="http://api.themoviedb.org/3/";
        final String apiKey="videos?api_key=df6f8e7dfca7d7dc1b2e67a4361ce04e";
        Intent newintent=getIntent();
        String newvalue=newintent.getStringExtra("title");
        id=newintent.getStringExtra("id");
        ArrayList<MovieAllDetails> movieDetailsGlobal=(ArrayList<MovieAllDetails>)newintent.getSerializableExtra("MyClass");
        String original_title=null;
        String posterPath=null;
        String releaseDate=null;
        String popularity=null;
        String storyLine=null;
        Log.e(TAG, "newValue: " +newvalue + "id: " + id + "movieDetailsGlobal:" + movieDetailsGlobal.size());

        for(int i=0;i<movieDetailsGlobal.size();i++) {
            if(id.equalsIgnoreCase(movieDetailsGlobal.get(i).getId())){
                original_title=movieDetailsGlobal.get(i).getOriginal_title();
                posterPath=imageServerPath+movieDetailsGlobal.get(i).getPoster_path();
                releaseDate=movieDetailsGlobal.get(i).getRelease_date();
                popularity=movieDetailsGlobal.get(i).getPopularity();
                storyLine=movieDetailsGlobal.get(i).getOverview();
            }
        }
        Log.e(TAG, "releaseDate: " + releaseDate + "posterPath: " + id + "posterPath:" + posterPath);

        //Ttile of the movie
        TextView textViewValue = (TextView)findViewById(R.id.original_title);
        textViewValue.setText(original_title);
        //Poster of the movie
        ImageView imageViewObj = (ImageView)findViewById(R.id.banner);
        Picasso.with(this).load(posterPath).into(imageViewObj);
        //Release Date of the Movie
        TextView release_date = (TextView)findViewById(R.id.release_date);
        release_date.setText(releaseDate);
        //Story Line of the movie
        TextView storyLineDetails = (TextView)findViewById(R.id.plot_synopsis);
        storyLineDetails.setText(storyLine);

        TextView popularityGain = (TextView)findViewById(R.id.popularity);
        popularityGain.setText(popularity);

       Button playTrailor =new Button(this);
        playTrailor.setId(Integer.parseInt(id));
        playTrailor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        playTrailor.setText("Play Trailer");
        playTrailor.setBackground(Drawable.createFromPath("#64FFDA"));
        playTrailor.setTextColor(Integer.parseInt("000000"));
        playTrailor.setTextSize(20);
        LinearLayout layout = (LinearLayout) findViewById(R.id.detailsViewLayout);
        layout.addView(playTrailor);

        Button playTrailorButton = (Button)findViewById(R.id.button);
        playTrailorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                watchYoutubeVideo(watchTrailerPath+id+apiKey);
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
        if (id == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Will be use for the YouTube display.
    public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(id));
            /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);*/
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    public void playTrailer(final String id){
        Button playTrailor = (Button)findViewById(Integer.parseInt(id));
        playTrailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                watchYoutubeVideo(watchTrailerPath+id+apiKey);
            }
        });
    }
}
