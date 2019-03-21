package com.ibrahim.soaqtask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ibrahim.soaqtask.model.Categories;

import java.util.List;

public class CategoriesGrideAdapter extends BaseAdapter {
    private Context mContext;
    private List<Categories> mCategories;

    private OnItemClickListener mOnItemClickListener;

    // Constructor
    public CategoriesGrideAdapter(Context c, List<Categories> categories) {
        mContext = c;
        this.mCategories = categories;
    }

    public int getCount() {
        return mCategories.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Categories currentCategories =mCategories.get(position);
        View gridView;

        if (convertView == null) {

            gridView = new View(mContext);

            gridView = inflater.inflate(R.layout.cat_view_holder, null);


        } else {
//            Log.d(TAG, "getView: ------------------");
            gridView = (View) convertView;
        }
        ImageView catImageView = gridView.findViewById(R.id.cat_imageView);
        TextView catTitle = gridView.findViewById(R.id.cat_title);
        final ProgressBar mProgressBar = gridView.findViewById(R.id.progressBar);

        catTitle.setText(currentCategories.getTitleAr()+"("+currentCategories.getProductCount()+")");

        Glide.with(mContext)
                .load(currentCategories.getPhotoUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(catImageView);


        return gridView;

    }

    public interface OnItemClickListener{
        void onItemClickListener(int positio);
    }


}
