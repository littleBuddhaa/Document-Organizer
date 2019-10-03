package com.bellatrix.aditi.documentorganizer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bumptech.glide.Glide;

/**
 * Created by Aditi on 14-04-2019.
 */

public class ImageSearchAdapter extends RecyclerView.Adapter<ImageSearchAdapter.ImageSearchViewHolder> {

    private Cursor mCursor;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    ImageSearchAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        this.clickLister = listItemClickLister;
    }

    @Override
    public ImageSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_search_holder, parent, false);

        ImageSearchViewHolder imageSearchViewHolder = new ImageSearchViewHolder(view);
        return imageSearchViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageSearchViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ImageSearchViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        ImageView imageView;
        TextView title, folder;
        ImageSearchViewHolder(View view)
        {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imagebox);
            title = (TextView) view.findViewById(R.id.image_title);
            folder = (TextView) view.findViewById(R.id.image_category);
            view.setOnClickListener(this);
        }
        void bind(int position)
        {
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null*/

            byte[] image = mCursor.getBlob(mCursor.getColumnIndex(Contract.Documents.COLUMN_IMAGE));
            Glide.with((Context)clickLister)
                    .load(image)
                    .into(imageView);

            title.setText(mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_TITLE)));
            folder.setText(mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_CATEGORY)));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickLister.onListItemClick(position);
        }
    }
    public void swapCursor(Cursor cursor1)
    {
        if(mCursor!=null)
            mCursor.close();

        mCursor=cursor1;
        if(mCursor!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}
