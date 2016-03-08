package app.project.nanodrgree.android.udacity.com.moviereview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewActivity extends ArrayAdapter<MovieAllDetails> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<MovieAllDetails> data =new ArrayList<MovieAllDetails> ();

    public GridViewActivity(Context context, int layoutResourceId, ArrayList<MovieAllDetails> data) {
        super(context, layoutResourceId, data);
        Log.e("GridViewActivity", "data:" + data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    public void setGridData(ArrayList<MovieAllDetails>data){
        this.data=data;
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<MovieAllDetails>data){
        this.data=data;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        String imageUrlText="http://image.tmdb.org/t/p/w185/";
        Log.e("GridViewActivity", "row:" + data);

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.textLayout);
            holder.image = (ImageView) row.findViewById(R.id.imageLayout);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Log.e("GridViewActivity", "position:" + position);
        MovieAllDetails movieAllDetailsObj=data.get(position);
        holder.imageTitle.setText(movieAllDetailsObj.getOriginal_title());
        Picasso.with(context).load(imageUrlText+movieAllDetailsObj.getPoster_path()).into(holder.image);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
