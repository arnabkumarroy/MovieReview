package app.project.nanodrgree.android.udacity.com.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        Intent newintent=getIntent();
        String newvalue=newintent.getStringExtra("title");
        String id=newintent.getStringExtra("id");
        ArrayList<MovieAllDetails> movieDetailsGlobal=(ArrayList<MovieAllDetails>)newintent.getSerializableExtra("MyClass");
        String original_title=null;
        String posterPath=null;
        String releaseDate=null;
        String popularity=null;
        String storyLine=null;

        for(int i=0;i<movieDetailsGlobal.size();i++) {
            if(id.equalsIgnoreCase(movieDetailsGlobal.get(i).getId())){
                original_title=movieDetailsGlobal.get(i).getOriginal_title();
                posterPath=movieDetailsGlobal.get(i).getPoster_path();
                releaseDate=movieDetailsGlobal.get(i).getRelease_date();
                popularity=movieDetailsGlobal.get(i).getPopularity();
                storyLine=movieDetailsGlobal.get(i).getOverview();
            }
        }
        TextView textViewvalue = (TextView)findViewById(R.id.textnew);
        textViewvalue.setText(original_title);
        ImageView imageViewObj = (ImageView)findViewById(R.id.imageViewObj);
        Picasso.with(this).load(posterPath).into(imageViewObj);
        TextView release_date = (TextView)findViewById(R.id.release_date);
        release_date.setText(releaseDate);
        TextView storyLineDetails = (TextView)findViewById(R.id.plaot_synopsis);
        storyLineDetails.setText(storyLine);
        TextView popularityGain = (TextView)findViewById(R.id.release_date);
        popularityGain.setText(popularity);

        /*Button playTrailor = (Button)findViewById(R.id.PlayTrailer);
        playTrailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                watchYoutubeVideo("94VKgb1Cloc");
            }
        });*/

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
    /*public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }*/
}
