package com.bellatrix.aditi.documentorganizer.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.R;

import java.io.File;
//*****

public class MyCustomPagerAdapter extends PagerAdapter {
    private Context context;
    private Cursor mCursor,r;
    private int p;
    LayoutInflater mLayoutInflater;
    public MyCustomPagerAdapter(Context context, String folderName,int idx) {
        this.context = context;
        this.p = idx;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCursor = DBQueries.getImageByFolder(context, folderName);
        r = DBQueries.getImageByFolder(context, folderName);
        r.moveToPosition(idx);
        mCursor.moveToPosition(idx);
        ImageView imageView = new ImageView(context);
        r.moveToPosition(idx);

    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position ) {
        //position = p;
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        //ImageView imageView = new ImageView(context);
        r.moveToPosition(position);
        try {
            imageView.setImageURI(FileProvider.getUriForFile(this.context, "com.bellatrix.aditi.documentorganizer", new File((r.getString(r.getColumnIndex(Contract.Documents.COLUMN_IMAGE))))));
        }
        catch (Exception e)
        {

        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}