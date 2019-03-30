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

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bumptech.glide.Glide;

/**
 * Created by Aditi on 14-03-2019.
 */

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Cursor mCursor;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    ImageAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        this.clickLister = listItemClickLister;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_holder, parent, false);

        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        ImageView imageView;
        ImageViewHolder(View view)
        {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imagebox);
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
