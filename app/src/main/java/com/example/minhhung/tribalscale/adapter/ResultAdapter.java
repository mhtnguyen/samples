package com.example.minhhung.tribalscale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.minhhung.tribalscale.R;
import com.example.minhhung.tribalscale.model.Name;
import com.example.minhhung.tribalscale.model.Picture;
import com.example.minhhung.tribalscale.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by minhhung on 2/5/2017.
 */
public class ResultAdapter extends ArrayAdapter<Result> {

    List<Result> resultLists;
    Context context;
    private LayoutInflater mInflater;

    public ResultAdapter(Context context, List<Result> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        resultLists = objects;
    }

    @Override
    public Result getItem(int position) {
        return resultLists.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Result item = getItem(position);
        if(item !=null){
            Name name = item.getName();
            if(name != null){
                vh.textViewName.setText(name.getFirst()+" "+name.getLast());
            }
            vh.textViewEmail.setText(item.getEmail());
            Picture picture = item.getPicture();
            if(picture!=null) {
                Picasso.with(context).load(picture.getMedium()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);
            }
        }

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewEmail;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.textViewEmail);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail);
        }
    }
}
