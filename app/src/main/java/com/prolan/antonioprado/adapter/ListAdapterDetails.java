package com.prolan.antonioprado.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.prolan.antonioprado.app.AppController;
import com.prolan.antonioprado.model.Books;
import com.prolan.antonioprado.R;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Prolan on 27/06/2016.
 */

public class ListAdapterDetails extends BaseAdapter{

    private Activity activity;
    private List<Books> booksList;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListAdapterDetails(Activity activity, List<Books> booksList) {
        this.activity = activity;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_row,null);
        if(imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        //Inflating the view

        NetworkImageView imgLogo = (NetworkImageView) convertView.findViewById(R.id.imgLogo);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvAut = (TextView) convertView.findViewById(R.id.tvAuthor);

        imgLogo.setImageUrl( booksList.get(position).getImageURL(), imageLoader);
        tvTitle.setText(booksList.get(position).getTitle());
        tvAut.setText(booksList.get(position).getAuthor());

        return convertView;
    }
}
