package com.bellatrix.aditi.documentorganizer;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;

import java.util.ArrayList;

/**
 * Created by Aditi on 13-03-2019.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private Cursor mCursor;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    FolderAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        this.clickLister = listItemClickLister;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_holder, parent, false);

        FolderViewHolder caseViewHolder = new FolderViewHolder(view);
        return caseViewHolder;
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title;
        LinearLayout holder;
        FolderViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.folder_name);
            holder = (LinearLayout)view.findViewById(R.id.folder_holder);
            view.setOnClickListener(this);
        }
        void bind(int position)
        {
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null*/

            String splitedName[] =  mCursor.getString(mCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)).split("_");
            String folderName = "";
            for(String s: splitedName) {
                folderName = folderName + " " + s;
            }
            String color = mCursor.getString(mCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_COLOR));

            this.holder.setBackgroundColor(Color.parseColor(color));
            this.title.setText(folderName);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickLister.onListItemClick(position);
        }
    }
    public void swapCursor(Cursor cursor)
    {
        if(mCursor!=null)
            mCursor.close();

        mCursor = cursor;
        if(mCursor!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}
