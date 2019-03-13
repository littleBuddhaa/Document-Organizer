package com.bellatrix.aditi.documentorganizer;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aditi on 13-03-2019.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    //    private Cursor mCursorCase;
//    private int mNumberOfItems;
    private ArrayList<Folder> folders;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    FolderAdapter(onListItemClickLister listItemClickLister)
    {
//        this.mCursorCase = cursor1;
//        this.mNumberOfItems = n;
        this.folders = Constants.FOLDERS;
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
        return folders.size();
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
            Folder folder;
            if ((folder = folders.get(position))==null)
                return; // bail if returned null*/

            this.title.setText(folder.folderName);
            this.holder.setBackgroundColor(Color.parseColor(folder.color));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickLister.onListItemClick(position);
        }
    }
    public void swapList()
    {
        folders = Constants.FOLDERS;
        if(folders!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}
